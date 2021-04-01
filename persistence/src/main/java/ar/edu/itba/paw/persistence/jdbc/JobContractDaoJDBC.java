package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.models.JobContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class JobContractDaoJDBC implements JobContractDao {

    private final static RowMapper<JobContract> JOB_CONTRACT_ROW_MAPPER = (resultSet, rowNum) -> new JobContract(
            resultSet.getLong("postId"),
            resultSet.getLong("clientId"),
            resultSet.getString("description"),
            "");

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JobContractDaoJDBC(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("contract").usingGeneratedKeyColumns("id");

    }

    @Override
    public JobContract create(long postId, long clientId, long packageId, String description) {
        Date creation_date = new Date();
        Number key = jdbcInsert.executeAndReturnKey(new HashMap<String, Object>() {{
            put("post_id", postId);
            put("client_id", clientId);
            put("package_id",packageId);
            put("description", description);
            put("creation_date", new java.sql.Date(creation_date.getTime()));
            put("is_active",true);
        }});

        JobContract jobContract = new JobContract();
        jobContract.setClientId(clientId);
        jobContract.setPostId(postId);
        jobContract.setId(key.longValue());
        jobContract.setPackageId(packageId);
        jobContract.setDescription(description);
        jobContract.setImage("");
        jobContract.setCreationDate(creation_date);

        return jobContract;

    }

    @Override
    public Optional<JobContract> findById(long id) {
        return jdbcTemplate.query("SELECT * FROM contract WHERE id = ?", new Object[]{id},
                JOB_CONTRACT_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<List<JobContract>> findByClientId(long id) {
        return Optional.of(
                jdbcTemplate.query("SELECT * from contract WHERE client_id = ?", new Object[]{id},
                        JOB_CONTRACT_ROW_MAPPER));
    }

    @Override
    public Optional<List<JobContract>> findByProId(long id) {
        //TODO
        return Optional.of( jdbcTemplate.query(
                "SELECT * FROM contract " +
                        "INNER JOIN job_post jp ON jp.id = contract.post_id WHERE user_id = ?",
                new Object[]{id},
                JOB_CONTRACT_ROW_MAPPER));
    }

    @Override
    public Optional<List<JobContract>> findByPostId(long id) {

        return Optional.of(jdbcTemplate.query("SELECT * from contract WHERE post_id = ?", new Object[]{id},
                JOB_CONTRACT_ROW_MAPPER));
    }
}
