package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.utils.ImageDataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class UserDaoJDBC implements UserDao {

    private static List<UserAuth.Role> mapArrRoleToList(Object[] roles) {
        List<UserAuth.Role> list = new ArrayList<>();
        for(Object role: roles) {
            if (role != null)
                list.add(UserAuth.Role.values()[(int) role]);
        }
        return list;
    }

    private static List<JobPost.JobType> mapArrJobTypeToList(Object[] roles) {
        List<JobPost.JobType> list = new ArrayList<>();
        Arrays.stream(roles)
                .forEach(obj -> list.add(JobPost.JobType.values()[(int) obj]));
        return list;
    }

    private final static RowMapper<User> USER_ROW_MAPPER = (resultSet, rowNum) -> new User(
            resultSet.getLong("user_id"),
            resultSet.getString("user_email"),
            resultSet.getString("user_name"),
            resultSet.getString("user_phone"),
            resultSet.getBoolean("user_is_active"),
            resultSet.getBoolean("user_is_verified"),
            new EncodedImage(ImageDataConverter.getEncodedString(resultSet.getBytes("user_image")),
                    resultSet.getString("image_type")),
            resultSet.getTimestamp("user_creation_date").toLocalDateTime());

    private final static RowMapper<UserAuth> USER_AUTH_ROW_MAPPER = ((resultSet, i) -> new UserAuth(
            resultSet.getString("user_email"),
            resultSet.getString("user_password"),
            mapArrRoleToList((Object[]) resultSet.getArray("roles").getArray()),
            resultSet.getBoolean("user_is_verified")
    ));

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleJdbcInsert roleJdbcInsert;

    @Autowired
    public UserDaoJDBC(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("users").usingGeneratedKeyColumns("user_id");
        roleJdbcInsert = new SimpleJdbcInsert(ds).withTableName("user_role");
    }

    @Override
    public User register(String email, String password, String username, String phone, ByteImage image) {
        LocalDateTime creationDate = LocalDateTime.now();
        Map<String, Object> objectMap = new HashMap<>();

        objectMap.put("user_email", email);
        objectMap.put("user_password", password);
        objectMap.put("user_name", username);
        objectMap.put("user_phone", phone);
        objectMap.put("user_is_active", true);
        objectMap.put("user_is_verified", false);
        objectMap.put("user_image", image.getData());
        objectMap.put("image_type", image.getType());
        objectMap.put("user_creation_date", creationDate);

        Number key = jdbcInsert.executeAndReturnKey(objectMap);

        return new User(key.longValue(), email, username,
                phone, true, false,
                new EncodedImage(ImageDataConverter.getEncodedString(image.getData()), image.getType()), creationDate);
    }

    @Override
    public User register(String email, String password, String username, String phone) {
        return register(email, password, username, phone, new ByteImage(null, null));
    }

    @Override
    public Optional<User> findById(long id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE user_id = ?", new Object[]{id}, USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM users WHERE user_email = ?", new Object[]{email}, USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> updateUserByEmail(String email, String phone, String name) {
        jdbcTemplate.update("UPDATE users SET user_phone = ?, user_name = ? WHERE user_email = ?", phone, name, email);
        return jdbcTemplate.query("SELECT * FROM users WHERE user_email = ?", new Object[]{email}, USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> updateUserById(long id, String name, String phone) {
        jdbcTemplate.update("UPDATE users SET user_phone = ?, user_name = ? WHERE user_id = ?", phone, name, id);
        return jdbcTemplate.query("SELECT * FROM users WHERE user_id = ?", new Object[]{id}, USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> updateUserById(long id, String name, String phone, ByteImage image) {
        jdbcTemplate.update("UPDATE users SET user_phone = ?, user_name = ?, user_image = (?), image_type = ? WHERE user_id = ?;",
                phone, name, image.getData(), image.getType(), id);
        return jdbcTemplate.query("SELECT * FROM users WHERE user_id = ?", new Object[]{id}, USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<UserAuth> findAuthInfo(String email) {
        String sqlQuery = new StringBuilder()
                .append("SELECT user_email,user_password,array_agg(role_id) as roles, user_is_verified ")
                .append("FROM users LEFT JOIN user_role ur on users.user_id = ur.user_id ")
                .append("WHERE user_email = ? AND user_is_active = TRUE GROUP BY user_email,user_password, user_is_verified")
                .toString();

        return jdbcTemplate.query( sqlQuery, new Object[]{email}, USER_AUTH_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public void assignRole(long id, int role) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("user_id", id);
        objectMap.put("role_id", role);
        roleJdbcInsert.execute(objectMap);
    }

    @Override
    public List<UserAuth.Role> findRoles(long id) {
        return jdbcTemplate.query("SELECT array_agg(role_id) as roles FROM user_role WHERE user_id = ? GROUP BY user_id",
                new Object[]{id}, (resultSet, i) -> mapArrRoleToList((Object[])
                        resultSet.getArray("roles").getArray())).stream().findFirst().orElse(new ArrayList<>());
    }

    @Override
    public Optional<User> findUserByRoleAndId(UserAuth.Role role, long id) {
        String sqlQuery = new StringBuilder()
                .append("SELECT user_id,user_name,user_email,user_is_active,user_phone,user_image,image_type,user_is_verified,user_creation_date  ")
                .append("FROM users NATURAL JOIN user_role WHERE user_id = ? AND role_id = ? AND user_is_active = TRUE ")
                .toString();

        return jdbcTemplate.query( sqlQuery, new Object[]{id, role.ordinal()}, USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public void changeUserPassword(long id, String password) {
        jdbcTemplate.update("UPDATE users SET user_password = ? WHERE user_id = ?;", password, id);
    }

    @Override
    public void verifyUser(long id) {
        jdbcTemplate.update("UPDATE users SET user_is_verified = true WHERE user_id = ?;", id);
    }

    @Override
    public boolean deleteUser(long id) {
        return jdbcTemplate.update("UPDATE users SET user_is_active = FALSE WHERE user_id = ?;", id) > 0;
    }

    @Override
    public List<JobPost.JobType> findUserJobTypes(long id) {
        return jdbcTemplate.query("SELECT array_agg(DISTINCT post_job_type) AS job_types FROM users NATURAL JOIN job_post WHERE user_id = ?",
                new Object[]{id}, (resultSet, i) -> mapArrJobTypeToList((Object[])
                        resultSet.getArray("job_types").getArray())).stream().findFirst().orElse(new ArrayList<>());
    }

    @Override
    public int findUserRankingInJobType(long id, JobPost.JobType jobType) {
        String sqlQuery = new StringBuilder()
                .append("SELECT rank FROM (")
                .append("         SELECT user_id, ROW_NUMBER() OVER () AS rank")
                .append("         FROM (SELECT user_id")
                .append("               FROM job_cards")
                .append("               WHERE post_job_type = ?")
                .append("               GROUP BY user_id")
                .append("               ORDER BY SUM(post_contract_count) DESC")
                //                         ORDERBY esta en una subquery para que funcione correctamente con HSQLDB
                .append("              ) AS users_ordered_by_contract")
                .append("     ) AS rankings")
                .append(" WHERE user_id = ?")
                .toString();

        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{jobType.ordinal(), id}, Integer.class);
    }
}
