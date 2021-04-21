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

    private static Integer getLimit(int page){
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
                            resultSet.getString("user_image_type"))
            ),
            resultSet.getString("post_title"),
            resultSet.getString("post_available_hours"),
            JobPost.JobType.values()[resultSet.getInt("post_job_type")],
            JobPostDaoJDBC.auxiGetZones((Object[]) resultSet.getArray("zones").getArray()),
            resultSet.getDouble("rating"),
            resultSet.getBoolean("post_is_active"));

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
        Number key = jdbcInsert.executeAndReturnKey(new HashMap<String, Object>() {{
            put("user_id", userId);
            put("post_title", title);
            put("post_available_hours", availableHours);
            put("post_job_type", jobType.ordinal());
            put("post_is_active", true);
        }});

        User user = userDao.findById(userId).orElseThrow(UserNotFoundException::new);

        for (Zone zone : zones) {
            jdbcInsertZone.execute(new HashMap<String, Integer>() {{
                put("post_id", key.intValue());
                put("zone_id", zone.ordinal());
            }});
        }

        return new JobPost(key.longValue(), user, title, availableHours, jobType, zones, 0.0,true);
    }

    @Override
    public Optional<JobPost> findById(long id) {
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE post_id = ?",
                new Object[]{id}, JOB_POST_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<JobPost> findByUserId(long id,int page) {
        Integer limit =getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE user_id = ? LIMIT ? OFFSET ?",
                new Object[]{id,limit,offset}, JOB_POST_ROW_MAPPER);
    }

    @Override
    public List<JobPost> findByJobType(JobPost.JobType jobType,int page) {
        Integer limit =getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE post_job_type = ? LIMIT ? OFFSET ?",
                new Object[]{jobType.ordinal(),limit,offset}, JOB_POST_ROW_MAPPER);
    }

    @Override
    public List<JobPost> findByZone(JobPost.Zone zone, int page) {
        Integer limit =getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE ? = ANY(zones) LIMIT ? OFFSET ?",
                new Object[]{zone.ordinal(),limit,offset}, JOB_POST_ROW_MAPPER);
    }


    @Override
    public List<JobPost> findAll(int page) {
        Integer limit =getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        return jdbcTemplate.query(
                "SELECT * FROM full_post LIMIT ? OFFSET ?",new Object[]{limit,offset},
                JOB_POST_ROW_MAPPER);
    }
    
    
    @Override
    public List<JobPost> search(String title, Zone zone,int page) {
        Integer limit =getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        title = "%" + title + "%";
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE upper(post_title) LIKE upper(?) AND ? = ANY(zones) LIMIT ? OFFSET ?",
                new Object[]{title,zone.ordinal(),limit,offset},
                JOB_POST_ROW_MAPPER
        );
    }

    @Override
    public List<JobPost> searchWithCategory(String title, Zone zone, JobPost.JobType jobType,int page) {
        title = "%" + title + "%";
        Integer limit =getLimit(page);
        int offset = page == HirenetUtils.ALL_PAGES ? 0 : HirenetUtils.PAGE_SIZE * page;
        return jdbcTemplate.query(
                "SELECT * FROM full_post WHERE upper(post_title) LIKE upper(?) AND ? = ANY(zones) AND post_job_type = ? LIMIT ? OFFSET ?",
                new Object[]{title,zone.ordinal(), jobType.ordinal(),limit,offset},
                JOB_POST_ROW_MAPPER
        );
    }

}
