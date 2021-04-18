package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class UserDaoJDBC implements UserDao {

    private static List<UserAuth.Role> mapArrToList(Object[] roles){
        List<UserAuth.Role> list = new ArrayList<>();
        Arrays.stream(roles)
                .forEach(obj -> list.add(UserAuth.Role.values()[(int) obj]));
        return list;
    }

    private final static RowMapper<User> USER_ROW_MAPPER = (resultSet, rowNum) -> new User(
            resultSet.getLong("user_id"),
            resultSet.getString("user_email"),
            resultSet.getString("user_name"),
//            resultSet.getString("user_image"),
            "",
            resultSet.getString("user_phone"),
            resultSet.getBoolean("user_is_active"));

    private final static RowMapper<UserAuth> USER_AUTH_ROW_MAPPER = ((resultSet, i) -> new UserAuth(
            resultSet.getString("user_email"),
            resultSet.getString("user_password"),
            mapArrToList((Object[]) resultSet.getArray("roles").getArray())
            ));

    private final RowMapper<Review> REVIEW_ROW_MAPPER = (resultSet, i) ->
            new Review(resultSet.getInt("rate"), resultSet.getString("review_title"),
                    resultSet.getString("review_description"));

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
    public User register(String email, String password, String username, String phone) {
        Number key =  jdbcInsert.executeAndReturnKey(new HashMap<String,Object>(){{
            put("user_email",email);
            put("user_password",password);
            put("user_name",username);
            put("user_image","");
            put("user_phone",phone);
            put("user_is_active",true);
        }});

        return new User(key.longValue(),email,username,"",phone,true);
    }

    @Override
    public Optional<User> findById(long id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE user_id = ?",new Object[]{id},USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM users WHERE user_email = ?",new Object[]{email},USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> updateUserByEmail(String email,String phone, String name){
        jdbcTemplate.update("UPDATE users SET user_phone = ?, user_name = ? WHERE user_email = ?", phone,name,email);
        return jdbcTemplate.query("SELECT * FROM users WHERE user_email = ?",new Object[]{email},USER_ROW_MAPPER).stream().findFirst();
    }
    @Override
    public Optional<User> updateUserById(long id, String phone, String name){
        jdbcTemplate.update("UPDATE users SET user_phone = ?, user_name = ? WHERE user_id = ?", phone,name,id);
        return jdbcTemplate.query("SELECT * FROM users WHERE user_id = ?",new Object[]{id},USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<UserAuth> findAuthInfo(String email) {
        return jdbcTemplate.query("SELECT user_email,user_password,array_agg(role_id) as roles " +
                "FROM users NATURAL JOIN user_role WHERE user_email = ? GROUP BY user_email,user_password",new Object[]{email},USER_AUTH_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public void assignRole(long id, int role) {
        roleJdbcInsert.execute(new HashMap<String,Object>(){{
            put("user_id",id);
            put("role_id",role);
        }});
    }

    @Override
    public List<UserAuth.Role> findRoles(long id) {
        return jdbcTemplate.query("SELECT array_agg(role_id) as roles FROM user_role WHERE user_id = ? GROUP BY user_id",new Object[]{id},(resultSet, i) -> {
            return mapArrToList((Object[]) resultSet.getArray("roles").getArray());
        }).stream().findFirst().orElse(new ArrayList<>());
    }

    @Override
    public List<Review> findUserReviews(long id) {
        return jdbcTemplate.query(
                "SELECT rate, review_title, review_description " +
                        "FROM job_post NATURAL JOIN job_package NATURAL JOIN contract NATURAL JOIN review " +
                        "WHERE user_id = ?", new Object[]{id}, (resultSet, i) ->
                        new Review(resultSet.getInt("rate"), resultSet.getString("review_title"),
                                resultSet.getString("review_description")));
    }

    @Override
    public Optional<User> findUserByRoleAndId(UserAuth.Role role, long id) {
        return jdbcTemplate.query("SELECT user_id,user_name,user_email,user_is_active,user_phone  FROM users NATURAL JOIN user_role WHERE user_id = ? AND role_id = ? ",new Object[]{id,role.ordinal()},USER_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public Double findProfessionalAvgRate(long id){
        return jdbcTemplate.query("SELECT coalesce(avg(rate),0) as rating from review NATURAL JOIN contract NATURAL JOIN job_package NATURAL JOIN job_post NATURAL JOIN users WHERE user_id = ? GROUP BY user_id",new Object[]{id},(resultSet, i) -> resultSet.getDouble("rating")).stream().findFirst().orElse(0.0);
    }
}
