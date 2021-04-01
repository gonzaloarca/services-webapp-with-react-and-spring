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
            resultSet.getString("email"),
            resultSet.getString("username"),
            resultSet.getString("user_image"),
            resultSet.getString("phone"),
            resultSet.getBoolean("is_professional"));

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public UserDaoJDBC(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("users").usingGeneratedKeyColumns("id");
    }

    @Override
    public User register(String email, String username, String phone, boolean isProfessional) {
        Number key =  jdbcInsert.executeAndReturnKey(new HashMap<String,Object>(){{
            put("email",email);
            put("username",username);
            put("user_image","");
            put("phone",phone);
            put("is_professional",isProfessional);
            put("is_active",true);
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
        return jdbcTemplate.query("SELECT * FROM users WHERE id = ?",new Object[]{id},USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM users WHERE email = ?",new Object[]{email},USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public void switchRole(long id) {
        jdbcTemplate.query("UPDATE users SET is_professional = NOT is_professional WHERE id = ?",new Object[]{id},USER_ROW_MAPPER);
    }
}
