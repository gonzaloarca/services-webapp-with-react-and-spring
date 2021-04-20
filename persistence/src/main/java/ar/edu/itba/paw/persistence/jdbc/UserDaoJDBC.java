package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.utils.ImageDataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
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
            resultSet.getString("user_phone"),
            resultSet.getBoolean("user_is_active"),
            true, //TODO: implementar esto
            new EncodedImage(ImageDataConverter.getEncodedString(resultSet.getBytes("user_image")),
                    resultSet.getString("image_type"))
    );

    private final static RowMapper<UserAuth> USER_AUTH_ROW_MAPPER = ((resultSet, i) -> new UserAuth(
            resultSet.getString("user_email"),
            resultSet.getString("user_password"),
            mapArrToList((Object[]) resultSet.getArray("roles").getArray())
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
        Map<String, Object> objectMap = new HashMap<>();

        objectMap.put("user_email", email);
        objectMap.put("user_password", password);
        objectMap.put("user_name", username);
        objectMap.put("user_phone", phone);
        objectMap.put("user_is_active", true);
        objectMap.put("user_image", image.getData());
        objectMap.put("image_type", image.getType());

        Number key =  jdbcInsert.executeAndReturnKey(objectMap);

        return new User(key.longValue(), email, username,
                phone, true, true,  //TODO implementar isVerified
                new EncodedImage(ImageDataConverter.getEncodedString(image.getData()), image.getType()));
    }

    @Override
    public User register(String email, String password, String username, String phone) {
        return register(email, password, username, phone, new ByteImage(null, null));
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
    public Optional<User> updateUserById(long id, String name, String phone){
        jdbcTemplate.update("UPDATE users SET user_phone = ?, user_name = ? WHERE user_id = ?", phone,name,id);
        return jdbcTemplate.query("SELECT * FROM users WHERE user_id = ?",new Object[]{id},USER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<User> updateUserById(long id, String name, String phone, ByteImage image) {
        updateUserById(id, name, phone);
        jdbcTemplate.update("UPDATE users SET user_image = (?), image_type = ? WHERE user_id = ?;",
                image.getData(), image.getType(), id);
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
    public Optional<User> findUserByRoleAndId(UserAuth.Role role, long id) {
        return jdbcTemplate.query("SELECT user_id,user_name,user_email,user_is_active,user_phone,user_image,image_type  FROM users NATURAL JOIN user_role WHERE user_id = ? AND role_id = ? ",new Object[]{id,role.ordinal()},USER_ROW_MAPPER)
                .stream().findFirst();
    }

    @Override
    public void changeUserPassword(long id, String password) {
        jdbcTemplate.update("UPDATE users SET user_password = ? WHERE user_id = ?;", password, id);
    }
}
