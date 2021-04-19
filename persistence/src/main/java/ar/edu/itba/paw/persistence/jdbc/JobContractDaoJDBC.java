package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.interfaces.dao.JobPackageDao;
import ar.edu.itba.paw.interfaces.dao.JobPostDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.*;
import exceptions.JobPackageNotFoundException;
import exceptions.JobPostNotFoundException;
import exceptions.UserNotFoundException;
import ar.edu.itba.paw.persistence.utils.ImageDataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class JobContractDaoJDBC implements JobContractDao {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JobPackageDao jobPackageDao;

    @Autowired
    private JobPostDao jobPostDao;

    private final static RowMapper<JobContract> JOB_CONTRACT_ROW_MAPPER = (resultSet, in) -> new JobContract(
            resultSet.getLong("contract_id"),
            new User(
                    resultSet.getLong("client_id"),
                    resultSet.getString("client_email"),
                    resultSet.getString("client_name"),
                    resultSet.getString("client_phone"),
                    resultSet.getBoolean("client_is_active"),
                    true,   //TODO implementar
                    new EncodedImage(ImageDataConverter.getEncodedString(resultSet.getBytes("client_image")),
                            resultSet.getString("client_image_type"))
            ),
            new JobPackage(
                    resultSet.getLong("package_id"),
                    resultSet.getLong("post_id"),
                    resultSet.getString("package_title"),
                    resultSet.getString("package_description"),
                    resultSet.getDouble("package_price"),
                    JobPackage.RateType.values()[resultSet.getInt("package_rate_type")],
                    resultSet.getBoolean("package_is_active")
            ), new User(
                    resultSet.getLong("professional_id"),
                    resultSet.getString("professional_email"),
                    resultSet.getString("professional_name"),
                    resultSet.getString("professional_phone"),
                    resultSet.getBoolean("professional_is_active"),
                    true,       //TODO implementar
                    new EncodedImage(ImageDataConverter.getEncodedString(resultSet.getBytes("professional_image")),
                            resultSet.getString("professional_image_type"))
            ),
            resultSet.getDate("creation_date"),
            resultSet.getString("contract_description"),
            new ByteImage(resultSet.getBytes("image_data"), resultSet.getString("image_type"))
    );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JobContractDaoJDBC(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("contract").usingGeneratedKeyColumns("contract_id");

    }

    @Override
    public JobContract create(long clientId, long packageId, String description) {
        return create(clientId, packageId, description, new ByteImage(null ,null));
    }

    @Override
    public JobContract create(long clientId, long packageId, String description, ByteImage image) {
        Date creationDate = new Date();
        Map<String,Object> objectMap = new HashMap<>();

        objectMap.put("client_id", clientId);
        objectMap.put("package_id", packageId);
        objectMap.put("contract_description", description);
        objectMap.put("creation_date", new java.sql.Date(creationDate.getTime()));
        objectMap.put("image_data", image.getData());
        objectMap.put("image_type", image.getType());

        Number key = jdbcInsert.executeAndReturnKey(objectMap);

        JobPackage jobPackage = jobPackageDao.findById(packageId).orElseThrow(JobPackageNotFoundException::new);

        JobPost jobPost = jobPostDao.findById(jobPackage.getPostId()).orElseThrow(JobPostNotFoundException::new);

        User client = userDao.findById(clientId).orElseThrow(UserNotFoundException::new);
        User professional = userDao.findById(jobPost.getUser().getId()).orElseThrow(UserNotFoundException::new);

        return new JobContract(key.longValue(), client, jobPackage, professional, creationDate, description, image);
    }

    @Override
    public Optional<JobContract> findById(long id) {
        return jdbcTemplate.query(
                "SELECT * FROM full_contract WHERE contract_id = ?",
                new Object[]{id}, JOB_CONTRACT_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<JobContract> findByClientId(long id) {
        return jdbcTemplate.query(
                "SELECT * FROM full_contract WHERE client_id = ?",
                new Object[]{id}, JOB_CONTRACT_ROW_MAPPER);
    }

    @Override
    public List<JobContract> findByProId(long id) {
        return jdbcTemplate.query(
                "SELECT * FROM full_contract WHERE professional_id = ?",
                new Object[]{id}, JOB_CONTRACT_ROW_MAPPER);
    }

    @Override
    public List<JobContract> findByPostId(long id) {

        return jdbcTemplate.query(
                "SELECT * FROM full_contract WHERE post_id = ?",
                new Object[]{id}, JOB_CONTRACT_ROW_MAPPER);
    }

    @Override
    public List<JobContract> findByPackageId(long id) {

        return jdbcTemplate.query(
                "SELECT * FROM full_contract WHERE package_id = ?",
                new Object[]{id}, JOB_CONTRACT_ROW_MAPPER);
    }

    @Override
    public int findContractsQuantityByProId(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) " +
                        "FROM contract " +
                        "NATURAL JOIN job_package " +
                        "NATURAL JOIN (SELECT post_id, user_id AS professional_id FROM job_post) AS posts " +
                        "WHERE professional_id = ?", new Object[]{id}, Integer.class);
    }

    @Override
    public int findContractsQuantityByPostId(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) " +
                        "FROM contract " +
                        "NATURAL JOIN job_package " +
                        "WHERE post_id = ?", new Object[]{id}, Integer.class);
    }

}
