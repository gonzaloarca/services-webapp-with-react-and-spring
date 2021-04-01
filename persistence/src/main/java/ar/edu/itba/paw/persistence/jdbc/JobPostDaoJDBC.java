package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.dao.JobPostDao;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class JobPostDaoJDBC implements JobPostDao {

    private final static RowMapper<JobPost> JOB_POST_ROW_MAPPER = (resultSet, rowNum) -> new JobPost(
            resultSet.getLong("email"),
            resultSet.getString("title"),
            resultSet.getString("available_hours"),
            JobPost.JobType.values()[resultSet.getInt("job_type")]);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JobPostDaoJDBC(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("job_post").usingGeneratedKeyColumns("id");
    }

    @Override
    public JobPost create(long userId, String title, String availableHours, JobPost.JobType jobType) {
        Number key = jdbcInsert.executeAndReturnKey(new HashMap<String,Object>(){{
            put("user_id",userId);
            put("title",title);
            put("available_hours",availableHours);
            put("job_type",jobType);
            put("is_active",true);
        }});

        JobPost jobPost = new JobPost();
        jobPost.setId(key.longValue());
        jobPost.setUserId(userId);
        jobPost.setTitle(title);
        jobPost.setAvailableHours(availableHours);
        jobPost.setJobType(jobType);
        jobPost.setActive(true);
        return jobPost;
    }

    @Override
    public Optional<JobPost> findById(long id) {
        return jdbcTemplate.query("SELECT * FROM job_post WHERE id = ?",new Object[]{id},JOB_POST_ROW_MAPPER).stream().findFirst();
    }
    @Override
    public Optional<List<JobPost>> findByUserId(long id) {
        return Optional.of(jdbcTemplate.query("SELECT * FROM job_post WHERE user_id = ?",new Object[]{id},JOB_POST_ROW_MAPPER));
    }

    @Override
    public Optional<List<JobPost>> findByJobType(JobPost.JobType jobType) {
        return Optional.of(jdbcTemplate.query("SELECT * FROM job_post WHERE job_type = ?",new Object[]{jobType},JOB_POST_ROW_MAPPER));
    }

    @Override
    public Optional<List<JobPost>> findByZone(Zone zone) {
        return Optional.of(jdbcTemplate.query("SELECT * FROM job_post INNER JOIN post_zone pz on job_post.id = pz.post_id AND pz.zone_id  = ?",
                new Object[]{zone},JOB_POST_ROW_MAPPER));
    }

    @Override
    public Optional<List<JobPost>> findAll() {
        return Optional.of(jdbcTemplate.query("SELECT * FROM job_post",JOB_POST_ROW_MAPPER));
    }
}
