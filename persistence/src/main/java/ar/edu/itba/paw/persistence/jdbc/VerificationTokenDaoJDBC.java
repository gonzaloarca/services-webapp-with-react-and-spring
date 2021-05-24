package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.dao.VerificationTokenDao;
import ar.edu.itba.paw.models.EncodedImage;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import ar.edu.itba.paw.persistence.utils.ImageDataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
/*
@Repository
public class VerificationTokenDaoJDBC implements VerificationTokenDao {

	private final static RowMapper<VerificationToken> TOKEN_ROW_MAPPER = (resultSet, i) -> new VerificationToken(
			resultSet.getString("token"),
			new User(
					resultSet.getLong("user_id"),
					resultSet.getString("user_email"),
					resultSet.getString("user_name"),
					resultSet.getString("user_phone"),
					resultSet.getBoolean("user_is_active"),
					resultSet.getBoolean("user_is_verified"),
					new EncodedImage(ImageDataConverter.getEncodedString(resultSet.getBytes("user_image")),
							resultSet.getString("image_type")),
                    resultSet.getTimestamp("user_creation_date").toLocalDateTime()),
			Instant.ofEpochMilli(resultSet.getDate("creation_date").getTime()
					+ resultSet.getTime("creation_date").getTime())
	);

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	@Autowired
	public VerificationTokenDaoJDBC(DataSource ds) {
		this.jdbcTemplate = new JdbcTemplate(ds);
		this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("verification_token")
				.usingGeneratedKeyColumns("token_id", "creation_date");
	}

	@Override
	public VerificationToken create(long user_id) {
		Map<String, Object> objectMap = new HashMap<>();

		objectMap.put("user_id", user_id);
		objectMap.put("token", UUID.randomUUID().toString());

		jdbcInsert.execute(objectMap);

		return findByUserId(user_id).orElseThrow(RuntimeException::new);
	}

	@Override
	public Optional<VerificationToken> findByUserId(long user_id) {
		return jdbcTemplate.query("SELECT * FROM users NATURAL JOIN verification_token WHERE user_id = ?;",
				new Object[]{user_id}, TOKEN_ROW_MAPPER).stream().findFirst();
	}

	@Override
	public void deleteToken(long user_id) {
		jdbcTemplate.update("DELETE FROM verification_token WHERE user_id = ?;", user_id);
	}
}
*/