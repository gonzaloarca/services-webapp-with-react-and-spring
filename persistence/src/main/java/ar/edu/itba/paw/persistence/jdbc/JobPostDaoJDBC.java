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

    private final static RowMapper<JobPost> JOB_POST_ROW_MAPPER = (resultSet, rowNum) -> new JobPost(
            resultSet.getLong("post_id"),
            new User(
                    resultSet.getLong("user_id"),
                    resultSet.getString("user_email"),
                    resultSet.getString("user_name"),
                    resultSet.getString("user_phone"),
                    resultSet.getBoolean("user_is_active"),
                    resultSet.getBoolean("user_is_verified"),
                    new EncodedImage(ImageDataConverter.getEncodedString(resultSet.getBytes("user_image")),
                            resultSet.getString("user_image_type")),
                    resultSet.getTimestamp("user_creation_date").toLocalDateTime()),
            resultSet.getString("post_title"),
            resultSet.getString("post_available_hours"),
            JobPost.JobType.values()[resultSet.getInt("post_job_type")],
            auxiGetZones((Object[]) resultSet.getArray("zones").getArray()),
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
        List<Object> parameters = new ArrayList<>(Collections.singletonList(id));
        if (page != HirenetUtils.ALL_PAGES) {
            parameters.add(HirenetUtils.PAGE_SIZE);
            parameters.add(HirenetUtils.PAGE_SIZE * page);
        }
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE user_id = ? AND post_is_active = TRUE " +
                        (page == HirenetUtils.ALL_PAGES ? "" : "LIMIT ? OFFSET ?"),
                parameters.toArray(), JOB_POST_ROW_MAPPER);
    }

    @Override
    public List<JobPost> findByJobType(JobPost.JobType jobType, int page) {
        List<Object> parameters = new ArrayList<>(Collections.singletonList(jobType.ordinal()));
        if (page != HirenetUtils.ALL_PAGES) {
            parameters.add(HirenetUtils.PAGE_SIZE);
            parameters.add(HirenetUtils.PAGE_SIZE * page);
        }
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE post_job_type = ? AND post_is_active = TRUE " +
                        (page == HirenetUtils.ALL_PAGES ? "" : "LIMIT ? OFFSET ?"),
                parameters.toArray(), JOB_POST_ROW_MAPPER);
    }

    @Override
    public List<JobPost> findByZone(JobPost.Zone zone, int page) {
        List<Object> parameters = new ArrayList<>(Collections.singletonList(zone.ordinal()));
        if (page != HirenetUtils.ALL_PAGES) {
            parameters.add(HirenetUtils.PAGE_SIZE);
            parameters.add(HirenetUtils.PAGE_SIZE * page);
        }
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE ? IN(UNNEST(zones)) AND post_is_active = TRUE " +
                        (page == HirenetUtils.ALL_PAGES ? "" : "LIMIT ? OFFSET ?"),
                parameters.toArray(), JOB_POST_ROW_MAPPER);
    }


    @Override
    public List<JobPost> findAll(int page) {
        List<Object> parameters = new ArrayList<>();
        if (page != HirenetUtils.ALL_PAGES) {
            parameters.add(HirenetUtils.PAGE_SIZE);
            parameters.add(HirenetUtils.PAGE_SIZE * page);
        }
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE post_is_active = TRUE " +
                        (page == HirenetUtils.ALL_PAGES ? "" : "LIMIT ? OFFSET ?"),
                parameters.toArray(), JOB_POST_ROW_MAPPER);
    }

    @Override
    public int findSizeByUserId(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM full_post WHERE user_id = ? AND post_is_active = TRUE",
                new Object[]{id}, Integer.class);
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
