package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Optional;

@Repository
public class UserDaoJDBC implements UserDao {

    private final static RowMapper<User> USER_ROW_MAPPER = (resultSet, rowNum) -> new User(
            resultSet.getLong("user_id"),
            resultSet.getString("user_email"),
            resultSet.getString("user_name"),
//            resultSet.getString("user_image"),
            "",
            resultSet.getString("user_phone"),
            resultSet.getBoolean("user_is_professional"),
            resultSet.getBoolean("user_is_active"));

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public UserDaoJDBC(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("users").usingGeneratedKeyColumns("user_id");
    }

    @Override
    public User register(String email, String username, String phone, boolean isProfessional) {
        Number key =  jdbcInsert.executeAndReturnKey(new HashMap<String,Object>(){{
            put("user_email",email);
            put("user_name",username);
            put("user_image","");
            put("user_phone",phone);
            put("user_is_professional",isProfessional);
            put("user_is_active",true);
        }});
        User user = new User();
        user.setId(key.longValue());
        user.setEmail(email);
        user.setUsername(username);
        user.setUserImage("");
        user.setPhone(phone);
        user.setProfessional(isProfessional);
        user.setActive(true);
        return user;
    }

    @Override
    public Optional<User> findById(long id) {
        System.out.println("EN USER DAO");
        return jdbcTemplate.query("SELECT * FROM users WHERE user_id = ?",new Object[]{id},USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM users WHERE user_email = ?",new Object[]{email},USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> switchRole(long id) {
        jdbcTemplate.update("UPDATE users SET user_is_professional = NOT user_is_professional WHERE user_id = ?", id);
        return jdbcTemplate.query("SELECT  * FROM users WHERE user_id = ?",new Object[]{id},USER_ROW_MAPPER).stream().findFirst();
    }
}
