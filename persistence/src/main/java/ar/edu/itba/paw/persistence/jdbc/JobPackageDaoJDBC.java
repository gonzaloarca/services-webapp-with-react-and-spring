package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.dao.JobPackageDao;
import ar.edu.itba.paw.models.JobPackage;
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
public class JobPackageDaoJDBC implements JobPackageDao {

    private final static RowMapper<JobPackage> JOB_PACKAGE_ROW_MAPPER = (resultSet, rowNum) -> new JobPackage(
            resultSet.getLong("id"),
            resultSet.getLong("postId"),
            resultSet.getString("title"),
            resultSet.getString("description"),
            resultSet.getDouble("price"),
            JobPackage.RateType.values()[resultSet.getInt("rate_type")],
            resultSet.getBoolean("is_active"));

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JobPackageDaoJDBC(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("job_package").usingGeneratedKeyColumns("id");
    }

    @Override
    public JobPackage create(long postId, String title, String description, double price, JobPackage.RateType rateType) {
        Number key = jdbcInsert.executeAndReturnKey(new HashMap<String,Object>(){{
            put("post_id",postId);
            put("title",title);
            put("description",description);
            put("price",price);
            put("rate_type",rateType);
            put("is_active",true);
        }});

        JobPackage jobPackage = new JobPackage();
        jobPackage.setId(key.longValue());
        jobPackage.setPostId(postId);
        jobPackage.setTitle(title);
        jobPackage.setDescription(description);
        jobPackage.setPrice(price);
        jobPackage.setRateType(rateType);
        return jobPackage;
    }

    @Override
    public Optional<JobPackage> findById(long id) {
        return jdbcTemplate.query("SELECT * FROM job_package WHERE id = ?",new Object[]{id},JOB_PACKAGE_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<List<JobPackage>> findByPostId(long id) {
        return Optional.of(jdbcTemplate.query("SELECT * FROM job_package WHERE post_id = ?",new Object[]{id},JOB_PACKAGE_ROW_MAPPER));
    }
}
