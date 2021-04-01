package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDaoOriginal;
import ar.edu.itba.paw.models.UserOriginal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDaoOriginal {

    private final static RowMapper<UserOriginal> USER_ROW_MAPPER =
            (rs, rowNum) -> new UserOriginal(rs.getLong("userId"), rs.getString("username"), rs.getString("password"));

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("users").usingGeneratedKeyColumns("userid");

//        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (" +
//                "userid SERIAL PRIMARY KEY, " +
//                "username VARCHAR(100) NOT NULL," +
//                "password VARCHAR(100) NOT NULL" +
//                ")");
    }

    @Override
    public Optional<UserOriginal> get(long id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id = ?", new Object[]{id},
                USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public UserOriginal register(String username, String password) {
        Number key = jdbcInsert.executeAndReturnKey(new HashMap<String, String>() {{
            put("username", username);
            put("password", password);
        }});

        UserOriginal user = new UserOriginal();
        user.setId(key.longValue());
        user.setName(username);
        user.setPassword(password);

        return user;
    }

    @Override
    public Optional<UserOriginal> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM users WHERE username = ?", new Object[]{name},
                USER_ROW_MAPPER).stream().findFirst();
    }
}
