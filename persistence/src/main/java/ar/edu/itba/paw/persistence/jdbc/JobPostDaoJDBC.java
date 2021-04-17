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

        return new JobPost(key.longValue(), user, title, availableHours, jobType, zones, true);
    }

    @Override
    public Optional<JobPost> findById(long id) {
        return jdbcTemplate.query(
                "SELECT post_id,user_id,post_title,post_available_hours,post_job_type,array_agg(zone_id) as zones,user_email,user_name,user_phone,user_is_active,post_is_active FROM job_post " +
                        "NATURAL JOIN users " +
                        "NATURAL JOIN post_zone WHERE post_id = ? " +
                        "GROUP BY post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active,user_email,user_name,user_phone,user_is_active",
                new Object[]{id}, JOB_POST_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<List<JobPost>> findByUserId(long id) {
        return Optional.of(jdbcTemplate.query(
                "SELECT post_id,user_id,post_title,post_available_hours,post_job_type,array_agg(zone_id) as zones,user_email,user_name,user_phone,user_is_active,post_is_active FROM job_post " +
                        "NATURAL JOIN users " +
                        "NATURAL JOIN post_zone WHERE user_id = ? " +
                        "GROUP BY post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active,user_email,user_name,user_phone,user_is_active",
                new Object[]{id}, JOB_POST_ROW_MAPPER));
    }

    @Override
    public Optional<List<JobPost>> findByJobType(JobPost.JobType jobType) {
        return Optional.of(jdbcTemplate.query(
                "SELECT post_id,user_id,post_title,post_available_hours,post_job_type,array_agg(zone_id) as zones,user_email,user_name,user_phone,user_is_active,post_is_active FROM job_post " +
                        "NATURAL JOIN users " +
                        "NATURAL JOIN post_zone WHERE post_job_type = ? " +
                        "GROUP BY post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active,user_email,user_name,user_phone,user_is_active",
                new Object[]{jobType.ordinal()}, JOB_POST_ROW_MAPPER));
    }

    @Override
    public Optional<List<JobPost>> findByZone(JobPost.Zone zone) {
        return Optional.of(jdbcTemplate.query(
                "SELECT post_id,user_id,post_title,post_available_hours,post_job_type,array_agg(zone_id) as zones,user_email,user_name,user_phone,user_is_active,post_is_active FROM job_post " +
                        "NATURAL JOIN users " +
                        "NATURAL JOIN post_zone WHERE zone_id = ? " +
                        "GROUP BY post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active,user_email,user_name,user_phone,user_is_active",
                new Object[]{zone.ordinal()}, JOB_POST_ROW_MAPPER));
    }

    @Override
    public Optional<List<JobPost>> findAll() {
        return Optional.of(jdbcTemplate.query(
                "SELECT post_id,user_id,post_title,post_available_hours,post_job_type,array_agg(zone_id) as zones,user_email,user_name,user_phone,user_is_active,post_is_active FROM job_post " +
                "NATURAL JOIN users " +
                        "NATURAL JOIN post_zone " +
                        "GROUP BY post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active,user_email,user_name,user_phone,user_is_active",
                JOB_POST_ROW_MAPPER));
    }

    @Override
    public Optional<List<JobPost>> search(String title, Zone zone) {
        title = "%" + title + "%";
        return Optional.of(jdbcTemplate.query(
                "SELECT post_id,user_id,post_title,post_available_hours,post_job_type,array_agg(zone_id) as zones,user_email,user_name,user_phone,user_is_active,post_is_active FROM job_post " +
                        "NATURAL JOIN users " +
                        "NATURAL JOIN post_zone WHERE zone_id = ? AND UPPER(post_title) LIKE UPPER(?)" +
                        "GROUP BY post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active,user_email,user_name,user_phone,user_is_active",
                new Object[]{zone.ordinal(),title},
                JOB_POST_ROW_MAPPER
        ));
    }

    @Override
    public Optional<List<JobPost>> searchWithCategory(String title, Zone zone, JobPost.JobType jobType) {
        title = "%" + title + "%";
        return Optional.of(jdbcTemplate.query(
                "SELECT post_id,user_id,post_title,post_available_hours,post_job_type,array_agg(zone_id) as zones," +
                        "user_email,user_name,user_phone,user_is_active,post_is_active FROM job_post " +
                        "NATURAL JOIN users " +
                        "NATURAL JOIN post_zone WHERE zone_id = ? AND UPPER(post_title) LIKE UPPER(?) AND post_job_type = ? " +
                        "GROUP BY post_id,user_id,post_title,post_available_hours,post_job_type,post_is_active,user_email,user_name,user_phone,user_is_active",
                new Object[]{zone.ordinal(), title, jobType.ordinal()},
                JOB_POST_ROW_MAPPER
        ));
    }

    @Override
    public Optional<List<Review>> findAllReviews(long id) {
        return Optional.of(jdbcTemplate.query(
                "SELECT rate, review_title, review_description " +
                        "FROM job_post NATURAL JOIN job_package NATURAL JOIN contract NATURAL JOIN review " +
                        "WHERE post_id = ?", new Object[]{id}, (resultSet, i) ->
                        new Review(resultSet.getInt("rate"), resultSet.getString("review_title"),
                                resultSet.getString("review_description"))));
    }
}
