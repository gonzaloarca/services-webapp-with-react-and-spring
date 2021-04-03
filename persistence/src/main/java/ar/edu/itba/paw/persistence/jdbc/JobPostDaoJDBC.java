package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.dao.JobPostDao;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPost.Zone;
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
    @Override
    public JobPost create(long userId, String title, String availableHours, JobPost.JobType jobType, List<Zone> zones) {
        return null;
    }

    @Override
    public Optional<JobPost> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<JobPost>> findByUserId(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<JobPost>> findByJobType(JobPost.JobType jobType) {
        return Optional.empty();
    }

    @Override
    public Optional<List<JobPost>> findByZone(Zone zone) {
        return Optional.empty();
    }

    @Override
    public Optional<List<JobPost>> findAll() {
        return Optional.empty();
    }

    private static List<Zone> auxiGetZones(Object[] objs){
        List<Zone> zones = new ArrayList<>();
        Arrays.stream(objs)
                .forEach(obj -> zones.add(Zone.values()[(int) obj]));
        return zones;
    }
//
//    private final static RowMapper<JobPost> JOB_POST_ROW_MAPPER = (resultSet, rowNum) -> new JobPost(
//            resultSet.getLong("id"),
//            resultSet.getLong("user_id"),
//            resultSet.getString("title"),
//            resultSet.getString("available_hours"),
//            JobPost.JobType.values()[resultSet.getInt("job_type")],
//            JobPostDaoJDBC.auxiGetZones((Object[]) resultSet.getArray("zones").getArray()),
//            resultSet.getBoolean("is_active"));
//
//    private final JdbcTemplate jdbcTemplate;
//    private final SimpleJdbcInsert jdbcInsert;
//    private final SimpleJdbcInsert jdbcInsertZone;
//
//    @Autowired
//    public JobPostDaoJDBC(DataSource ds) {
//        jdbcTemplate = new JdbcTemplate(ds);
//        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("job_post").usingGeneratedKeyColumns("id");
//        jdbcInsertZone = new SimpleJdbcInsert(ds).withTableName("post_zone");
//    }
//
//    @Override
//    public JobPost create(User userId, String title, String availableHours, JobPost.JobType jobType, List<Zone> zones) {
//        Number key = jdbcInsert.executeAndReturnKey(new HashMap<String,Object>(){{
//            put("user_id",userId);
//            put("title",title);
//            put("available_hours",availableHours);
//            put("job_type",jobType.ordinal());
//            put("is_active",true);
//        }});
//
//        for (Zone zone : zones) {
//            jdbcInsertZone.execute(new HashMap<String,Integer>(){{
//                put("post_id",key.intValue());
//                put("zone_id",zone.ordinal());
//            }});
//        }
//
//        JobPost jobPost = new JobPost();
//        jobPost.setId(key.longValue());
//        jobPost.setUser(userId);
//        jobPost.setTitle(title);
//        jobPost.setAvailableHours(availableHours);
//        jobPost.setJobType(jobType);
//        jobPost.setZones(zones);
//        jobPost.setActive(true);
//        return jobPost;
//    }
//
//    @Override
//    public Optional<JobPost> findById(long id) {
//        return jdbcTemplate.query("SELECT id,user_id,title,available_hours,job_type,array_agg(pz.zone_id) as zones,is_active FROM job_post INNER JOIN post_zone pz on job_post.id = pz.post_id WHERE id = ? GROUP BY job_post.id",new Object[]{id},JOB_POST_ROW_MAPPER).stream().findFirst();
//
//    }
//    @Override
//    public Optional<List<JobPost>> findByUserId(long id) {
//        return Optional.of(jdbcTemplate.query("SELECT * FROM job_post INNER JOIN post_zone pz on job_post.id = pz.post_id WHERE user_id = ? GROUP BY job_post.id",new Object[]{id},JOB_POST_ROW_MAPPER));
//    }
//
//    @Override
//    public Optional<List<JobPost>> findByJobType(JobPost.JobType jobType) {
//        return Optional.of(jdbcTemplate.query("SELECT * FROM job_post INNER JOIN post_zone pz on job_post.id = pz.post_id WHERE job_type = ? GROUP BY job_post.id",new Object[]{jobType},JOB_POST_ROW_MAPPER));
//    }
//
//    @Override
//    public Optional<List<JobPost>> findByZone(JobPost.Zone zone) {
//        return Optional.of(jdbcTemplate.query("SELECT * FROM job_post INNER JOIN post_zone pz on job_post.id = pz.post_id AND pz.zone_id  = ?",
//                new Object[]{zone},JOB_POST_ROW_MAPPER));
//    }
//
//    @Override
//    public Optional<List<JobPost>> findAll() {
//        return Optional.of(jdbcTemplate.query("SELECT * FROM job_post INNER JOIN post_zone pz on job_post.id = pz.post_id GROUP BY job_post.id",JOB_POST_ROW_MAPPER));
//    }
}
