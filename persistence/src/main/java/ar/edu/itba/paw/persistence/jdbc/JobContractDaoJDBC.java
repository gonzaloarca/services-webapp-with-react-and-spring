package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.interfaces.dao.JobPackageDao;
import ar.edu.itba.paw.interfaces.dao.JobPostDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JobContractDaoJDBC implements JobContractDao {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JobPackageDao jobPackageDao;

    @Autowired
    private JobPostDao jobPostDao;

    private final static RowMapper<JobContract> JOB_CONTRACT_ROW_MAPPER = (resultSet, in) -> {
        return new JobContract(
                resultSet.getLong("id"),
                new User(
                        resultSet.getLong("client_id"),
                        resultSet.getString("client_email"),
                        resultSet.getString("client_username"),
                        "",
                        resultSet.getString("client_phone"),
                        resultSet.getBoolean("client_is_professional"),
                        resultSet.getBoolean("client_is_active")
                ),
                new JobPackage(
                        resultSet.getLong("package_id"),
                        resultSet.getLong("post_id"),
                        resultSet.getString("title"),
                        resultSet.getString("package_description"),
                        resultSet.getDouble("price"),
                        JobPackage.RateType.values()[resultSet.getInt("rate_type")],
                        resultSet.getBoolean("package_is_active")
                ), new User(
                resultSet.getLong("professional_id"),
                resultSet.getString("professional_email"),
                resultSet.getString("professional_username"),
                "",
                resultSet.getString("professional_phone"),
                resultSet.getBoolean("professional_is_professional"),
                resultSet.getBoolean("professional_is_active")
        ),
                resultSet.getDate("creation_date"),
                resultSet.getString("description"),
                ""
        );
    };

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JobContractDaoJDBC(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("contract").usingGeneratedKeyColumns("id");

    }

    @Override
    public JobContract create(long clientId, long packageId, String description) {
        Date creationDate = new Date();
        Number key = jdbcInsert.executeAndReturnKey(new HashMap<String, Object>() {{
            put("client_id", clientId);
            put("package_id", packageId);
            put("description", description);
            put("creation_date", new java.sql.Date(creationDate.getTime()));
        }});

        //TODO: Cambiar excepciones por excepciones propias
        User client = userDao.findById(clientId).orElseThrow(NoSuchElementException::new);

        JobPackage jobPackage = jobPackageDao.findById(packageId).orElseThrow(NoSuchElementException::new);

        JobPost jobPost = jobPostDao.findById(jobPackage.getPostId()).orElseThrow(NoSuchElementException::new);

        //TODO: Revisar caso de nullPointerException
        User profesional = userDao.findById(jobPost.getUser().getId()).orElseThrow(NoSuchElementException::new);

        return new JobContract(key.longValue(), client, jobPackage, profesional, creationDate, description, "");
    }

    @Override
    public Optional<JobContract> findById(long id) {


        return jdbcTemplate.query(
                "SELECT * FROM contract" +
                        "    NATURAL JOIN" +
                        "    (SELECT id AS package_id, post_id, title, description AS package_description, price, rate_type,is_active AS package_is_active FROM job_package) AS packages" +
                        "    NATURAL JOIN" +
                        "    (SELECT id AS post_id,user_id AS professional_id FROM job_post) AS posts" +
                        "    NATURAL JOIN (SELECT id AS client_id, username AS client_username, email AS client_email," +
                        "                         phone AS client_phone, is_professional AS client_is_professional, is_active AS client_is_active FROM users)" +
                        "        AS clients" +
                        "    NATURAL JOIN (SELECT id AS professional_id, username AS professional_username, email AS professional_email," +
                        "                         phone AS professional_phone, is_professional AS professional_is_professional, is_active AS professional_is_active FROM users)" +
                        "        AS professionals " +
                        "WHERE contract.id = ?"
                , new Object[]{id}, JOB_CONTRACT_ROW_MAPPER).stream().findFirst();


    }

    @Override
    public Optional<List<JobContract>> findByClientId(long id) {
        return Optional.of(
                jdbcTemplate.query(
                        "SELECT * FROM contract" +
                                "    NATURAL JOIN" +
                                "    (SELECT id AS package_id, post_id, title, description AS package_description, price, rate_type,is_active AS package_is_active FROM job_package) AS packages" +
                                "    NATURAL JOIN" +
                                "    (SELECT id AS post_id,user_id AS professional_id FROM job_post) AS posts" +
                                "    NATURAL JOIN (SELECT id AS client_id, username AS client_username, email AS client_email," +
                                "                         phone AS client_phone, is_professional AS client_is_professional, is_active AS client_is_active FROM users)" +
                                "        AS clients" +
                                "    NATURAL JOIN (SELECT id AS professional_id, username AS professional_username, email AS professional_email," +
                                "                         phone AS professional_phone, is_professional AS professional_is_professional, is_active AS professional_is_active FROM users)" +
                                "        AS professionals " +
                                "WHERE client_id = ?"
                        , new Object[]{id},
                        JOB_CONTRACT_ROW_MAPPER));
    }

    @Override
    public Optional<List<JobContract>> findByProId(long id) {
        //TODO
        return Optional.of(jdbcTemplate.query(
                "SELECT * FROM contract" +
                        "    NATURAL JOIN" +
                        "    (SELECT id AS package_id, post_id, title, description AS package_description, price, rate_type,is_active AS package_is_active FROM job_package) AS packages" +
                        "    NATURAL JOIN" +
                        "    (SELECT id AS post_id,user_id AS professional_id FROM job_post) AS posts" +
                        "    NATURAL JOIN (SELECT id AS client_id, username AS client_username, email AS client_email," +
                        "                         phone AS client_phone, is_professional AS client_is_professional, is_active AS client_is_active FROM users)" +
                        "        AS clients" +
                        "    NATURAL JOIN (SELECT id AS professional_id, username AS professional_username, email AS professional_email," +
                        "                         phone AS professional_phone, is_professional AS professional_is_professional, is_active AS professional_is_active FROM users)" +
                        "        AS professionals " +
                        "WHERE professional_id = ?",
                new Object[]{id}, JOB_CONTRACT_ROW_MAPPER));
    }

    @Override
    public Optional<List<JobContract>> findByPostId(long id) {

        return Optional.of(jdbcTemplate.query(
                "SELECT * FROM contract" +
                        "    NATURAL JOIN" +
                        "    (SELECT id AS package_id, post_id, title, description AS package_description, price, rate_type,is_active AS package_is_active FROM job_package) AS packages" +
                        "    NATURAL JOIN" +
                        "    (SELECT id AS post_id,user_id AS professional_id FROM job_post) AS posts" +
                        "    NATURAL JOIN (SELECT id AS client_id, username AS client_username, email AS client_email," +
                        "                         phone AS client_phone, is_professional AS client_is_professional, is_active AS client_is_active FROM users)" +
                        "        AS clients" +
                        "    NATURAL JOIN (SELECT id AS professional_id, username AS professional_username, email AS professional_email," +
                        "                         phone AS professional_phone, is_professional AS professional_is_professional, is_active AS professional_is_active FROM users)" +
                        "        AS professionals " +
                        "WHERE post_id = ?"
                , new Object[]{id},
                JOB_CONTRACT_ROW_MAPPER));
    }

    @Override
    public Optional<List<JobContract>> findByPackageId(long id) {

        return Optional.of(jdbcTemplate.query(
                "SELECT * FROM contract" +
                        "    NATURAL JOIN" +
                        "    (SELECT id AS package_id, post_id, title, description AS package_description, price, rate_type,is_active AS package_is_active FROM job_package) AS packages" +
                        "    NATURAL JOIN" +
                        "    (SELECT id AS post_id,user_id AS professional_id FROM job_post) AS posts" +
                        "    NATURAL JOIN (SELECT id AS client_id, username AS client_username, email AS client_email," +
                        "                         phone AS client_phone, is_professional AS client_is_professional, is_active AS client_is_active FROM users)" +
                        "        AS clients" +
                        "    NATURAL JOIN (SELECT id AS professional_id, username AS professional_username, email AS professional_email," +
                        "                         phone AS professional_phone, is_professional AS professional_is_professional, is_active AS professional_is_active FROM users)" +
                        "        AS professionals " +
                        "WHERE package_id = ?"
                , new Object[]{id},
                JOB_CONTRACT_ROW_MAPPER));
    }
}
