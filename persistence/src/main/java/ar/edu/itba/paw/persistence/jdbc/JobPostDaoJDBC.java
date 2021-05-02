package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobPostDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.EncodedImage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPost.Zone;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.utils.ImageDataConverter;
import exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JobPostDaoJDBC implements JobPostDao {

    @Autowired
    private UserDao userDao;

    private static List<Zone> auxiGetZones(Object[] objs) {
        List<Zone> zones = new ArrayList<>();
        Arrays.stream(objs)
                .forEach(obj -> zones.add(Zone.values()[(int) obj]));
        return zones;
    }

    private static Integer getLimit(int page) {
        return page == HirenetUtils.ALL_PAGES ? null : HirenetUtils.PAGE_SIZE;
    }

    private final static RowMapper<JobPost> JOB_POST_ROW_MAPPER = (resultSet, rowNum) -> new JobPost(
            resultSet.getLong("post_id"),
            new User(
                    resultSet.getLong("user_id"),
                    resultSet.getString("user_email"),
                    resultSet.getString("user_name"),
                    resultSet.getString("user_phone"),
                    resultSet.getBoolean("user_is_active"),
                    true, //TODO: implementar esto
                    new EncodedImage(ImageDataConverter.getEncodedString(resultSet.getBytes("user_image")),
                            resultSet.getString("user_image_type")),
                    resultSet.getTimestamp("user_creation_date").toLocalDateTime()),
            resultSet.getString("post_title"),
            resultSet.getString("post_available_hours"),
            JobPost.JobType.values()[resultSet.getInt("post_job_type")],
            auxiGetZones((Object[]) resultSet.getArray("zones").getArray()),
//            new ArrayList<>(Arrays.asList(Zone.values()[0], Zone.values()[1])),
            resultSet.getDouble("rating"),
            resultSet.getBoolean("post_is_active"),
            resultSet.getTimestamp("post_creation_date").toLocalDateTime());

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleJdbcInsert jdbcInsertZone;

    @Autowired
    public JobPostDaoJDBC(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("job_post").usingGeneratedKeyColumns("post_id");
        jdbcInsertZone = new SimpleJdbcInsert(ds).withTableName("post_zone");
    }

    @Override
    public JobPost create(long userId, String title, String availableHours, JobPost.JobType jobType, List<Zone> zones) {
        LocalDateTime creationDate = LocalDateTime.now();
        Number key = jdbcInsert.executeAndReturnKey(new HashMap<String, Object>() {{
            put("user_id", userId);
            put("post_title", title);
            put("post_available_hours", availableHours);
            put("post_job_type", jobType.ordinal());
            put("post_is_active", true);
            put("post_creation_date", creationDate);
        }});

        User user = userDao.findById(userId).orElseThrow(UserNotFoundException::new);

        for (Zone zone : zones) {
            jdbcInsertZone.execute(new HashMap<String, Integer>() {{
                put("post_id", key.intValue());
                put("zone_id", zone.ordinal());
            }});
        }

        return new JobPost(key.longValue(), user, title, availableHours, jobType, zones, 0.0, true, creationDate);
    }

    @Override
    public Optional<JobPost> findById(long id) {
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE post_id = ? AND post_is_active = TRUE",
                new Object[]{id}, JOB_POST_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<JobPost> findByUserId(long id, int page) {
        Integer limit = getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE user_id = ? AND post_is_active = TRUE LIMIT ? OFFSET ?",
                new Object[]{id, limit, offset}, JOB_POST_ROW_MAPPER);
    }

    @Override
    public List<JobPost> findByJobType(JobPost.JobType jobType, int page) {
        Integer limit = getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE post_job_type = ? AND post_is_active = TRUE LIMIT ? OFFSET ?",
                new Object[]{jobType.ordinal(), limit, offset}, JOB_POST_ROW_MAPPER);
    }

    @Override
    public List<JobPost> findByZone(JobPost.Zone zone, int page) {
        Integer limit = getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE ? IN(UNNEST(zones)) AND post_is_active = TRUE LIMIT ? OFFSET ?",
                new Object[]{zone.ordinal(), limit, offset}, JOB_POST_ROW_MAPPER);
    }


    @Override
    public List<JobPost> findAll(int page) {
        Integer limit = getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE post_is_active = TRUE LIMIT ? OFFSET ?", new Object[]{limit, offset},
                JOB_POST_ROW_MAPPER);
    }

    @Override
    public List<JobPost> findRelatedJobPosts(long professional_id) {
        return jdbcTemplate.query(
                "SELECT * " +
                        "FROM full_post" +
                        "         NATURAL JOIN (" +
                        "    SELECT DISTINCT pro2_contracts.post_id, COUNT(DISTINCT pro2_contracts.client_id) AS clients_in_common" +
                        "    FROM full_contract pro1_contracts" +
                        "             JOIN full_contract pro2_contracts ON pro1_contracts.client_id = pro2_contracts.client_id" +
                        "    WHERE ? <> pro2_contracts.professional_id" +
                        "      AND pro2_contracts.contract_creation_date >= now()::date-30" +
                        "    GROUP BY pro2_contracts.post_id) AS recommended_posts " +
                        "WHERE post_is_active = TRUE" +
                        "  AND post_job_type IN (" +
                        "    SELECT DISTINCT post_job_type" +
                        "    FROM job_post" +
                        "    WHERE ? = user_id) " +
                        "ORDER BY clients_in_common DESC, contracts DESC"
                // Obtiene los posts de otros profesionales con el que comparte clientes(los cuales contrataron al otro
                // profesional en los ultimos 30 dias) y tipo de trabajo,
                // ordenados descendientemente por clientes en comun y luego por cantidad de contratos.
                , new Object[]{professional_id, professional_id}, JOB_POST_ROW_MAPPER);
    }

    @Override
    public List<JobPost> search(String query, Zone zone, int page) {
        Integer limit = getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        query = "%" + query + "%";
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE UPPER(post_title) LIKE UPPER(?) AND ? IN(UNNEST(zones)) AND post_is_active = TRUE LIMIT ? OFFSET ?",
                new Object[]{query, zone.ordinal(), limit, offset},
                JOB_POST_ROW_MAPPER
        );
    }

    @Override
    public List<JobPost> searchWithCategory(String query, Zone zone, JobPost.JobType jobType, int page) {
        query = "%" + query + "%";
        Integer limit = getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE UPPER(post_title) LIKE UPPER(?) AND ? IN(UNNEST(zones)) AND post_job_type = ? AND post_is_active = TRUE LIMIT ? OFFSET ?",
                new Object[]{query, zone.ordinal(), jobType.ordinal(), limit, offset},
                JOB_POST_ROW_MAPPER
        );
    }

    @Override
    public int findSizeByUserId(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM full_post WHERE user_id = ? AND post_is_active = TRUE",
                new Object[]{id}, Integer.class);
    }

    @Override
    public int findAllMaxPage() {
        Integer totalJobsCount = jdbcTemplate.queryForObject("SELECT COUNT(post_id) FROM full_post WHERE post_is_active = TRUE", Integer.class);
        return (int) Math.ceil((double) totalJobsCount / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findMaxPageByUserId(long id) {
        Integer totalJobsCount = jdbcTemplate.queryForObject("SELECT COUNT(post_id) FROM full_post WHERE user_id = ? AND post_is_active = TRUE",
                new Object[]{id}, Integer.class);
        return (int) Math.ceil((double) totalJobsCount / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findMaxPageSearch(String query, Zone zone, JobPost.JobType jobType) {
        query = "%" + query + "%";
        Integer totalJobsCount = jdbcTemplate.queryForObject("SELECT COUNT(post_id) FROM full_post " +
                        "WHERE UPPER(post_title) LIKE UPPER(?) AND ? IN(UNNEST(zones)) AND post_is_active = TRUE",
                new Object[]{query, zone.ordinal()}, Integer.class);
        return (int) Math.ceil((double) totalJobsCount / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public boolean updateById(long id, String title, String availableHours, JobPost.JobType jobType, List<Zone> zones) {
        int rowsAffected = jdbcTemplate.update("UPDATE job_post SET post_title = ? , post_available_hours = ? , post_job_type = ? WHERE post_id = ?", title, availableHours, jobType.ordinal(), id);
        jdbcTemplate.update("DELETE FROM post_zone WHERE post_id = ?", id);
        for (Zone zone : zones) {
            jdbcInsertZone.execute(new HashMap<String, Integer>() {{
                put("post_id", (int) id);
                put("zone_id", zone.ordinal());
            }});
        }
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteJobPost(long id) {
        return jdbcTemplate.update("UPDATE job_post SET post_is_active = FALSE WHERE post_id = ?", id) > 0;
    }

}
