package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.dao.JobPostDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPost.Zone;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
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

    private final static RowMapper<JobPost> JOB_POST_ROW_MAPPER = (resultSet, rowNum) -> new JobPost(
            resultSet.getLong("post_id"),
            new User(
                    resultSet.getLong("user_id"),
                    resultSet.getString("user_email"),
                    resultSet.getString("user_name"),
                    "",
                    resultSet.getString("user_phone"),
                    resultSet.getBoolean("user_is_active")
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

        //TODO: Cambiar excepciones por excepciones propias
        User user = userDao.findById(userId).orElseThrow(NoSuchElementException::new);

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
                "SELECT * FROM full_posts WHERE post_id = ?",
                new Object[]{id}, JOB_POST_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<JobPost> findByUserId(long id) {
        return jdbcTemplate.query(
                "SELECT * FROM full_posts WHERE user_id = ?",
                new Object[]{id}, JOB_POST_ROW_MAPPER);
    }

    @Override
    public List<JobPost> findByJobType(JobPost.JobType jobType) {
        return jdbcTemplate.query(
                "SELECT * FROM full_posts WHERE post_job_type = ?",
                new Object[]{jobType.ordinal()}, JOB_POST_ROW_MAPPER);
    }

    @Override
    public List<JobPost> findByZone(JobPost.Zone zone) {
        return jdbcTemplate.query(
                "SELECT * FROM full_posts WHERE ? = ANY(zones)",
                new Object[]{zone.ordinal()}, JOB_POST_ROW_MAPPER);
    }

    @Override
    public List<JobPost> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM full_posts",
                JOB_POST_ROW_MAPPER);
    }
    @Override
    public List<JobPost> search(String title, Zone zone) {
        title = "%" + title + "%";
        return jdbcTemplate.query(
                "SELECT * FROM full_posts WHERE upper(post_title) LIKE ? AND ? = ANY(zones)",
                new Object[]{zone.ordinal(),title},
                JOB_POST_ROW_MAPPER
        );
    }

    @Override
    public List<JobPost> searchWithCategory(String title, Zone zone, JobPost.JobType jobType) {
        title = "%" + title + "%";
        return jdbcTemplate.query(
                "SELECT * FROM full_posts WHERE upper(post_title) LIKE ? AND ? = ANY(zones) AND post_job_type = ?",
                new Object[]{title,zone.ordinal(), jobType.ordinal()},
                JOB_POST_ROW_MAPPER
        );
    }

    @Override
    public List<Review> findAllReviews(long id) {
        return jdbcTemplate.query(
                "SELECT rate, review_title, review_description " +
                        "FROM job_post NATURAL JOIN job_package NATURAL JOIN contract NATURAL JOIN review " +
                        "WHERE post_id = ?", new Object[]{id}, (resultSet, i) ->
                        new Review(resultSet.getInt("rate"), resultSet.getString("review_title"),
                                resultSet.getString("review_description")));
    }

    @Override
    public int findJobPostReviewSize(long id) {
        return jdbcTemplate.query("SELECT count(contract_id) as size FROM review NATURAL JOIN contract NATURAL JOIN job_package NATURAL JOIN job_post WHERE post_id = ?",new Object[]{id},(resultSet,i) -> resultSet.getInt("size")).stream().findFirst().orElse(0);
    }
}
