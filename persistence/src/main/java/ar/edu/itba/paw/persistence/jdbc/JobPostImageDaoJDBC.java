package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.dao.JobPostImageDao;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.EncodedImage;
import ar.edu.itba.paw.models.JobPostImage;
import ar.edu.itba.paw.persistence.utils.ImageDataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class JobPostImageDaoJDBC implements JobPostImageDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsertImage;

	private final static RowMapper<JobPostImage> JOB_POST_IMAGE_ROW_MAPPER = ((resultSet, i) -> new JobPostImage(
			resultSet.getLong("image_id"),
			resultSet.getLong("post_id"),
			new EncodedImage(ImageDataConverter.getEncodedString(resultSet.getBytes("image_data")),
					resultSet.getString("image_type"))
	));

	@Autowired
	public JobPostImageDaoJDBC(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsertImage = new SimpleJdbcInsert(ds).withTableName("post_image").usingGeneratedKeyColumns("image_id");
	}

	@Override
	public JobPostImage addImage(long postId, ByteImage image) {
		Map<String, Object> map = new HashMap<>();
		map.put("post_id", postId);
		map.put("image_data", image.getData());
		map.put("image_type", image.getType());

		Number imageId = jdbcInsertImage.executeAndReturnKey(map);

		return new JobPostImage(imageId.longValue(), postId,
				new EncodedImage(ImageDataConverter.getEncodedString(image.getData()), image.getType()));
	}

	@Override
	public List<JobPostImage> findByPostId(long postId) {
		return jdbcTemplate.query(
			"SELECT image_id, post_id, image_data, image_type " +
					"FROM post_image " +
					"WHERE post_id = ? " +
					"GROUP BY image_id;",
				new Object[]{postId},
				JOB_POST_IMAGE_ROW_MAPPER
		);
	}

	@Override
	public List<JobPostImage> getPostImage(long postId) {
		return jdbcTemplate.query(
				"SELECT image_id, post_id, image_data, image_type " +
						"FROM post_image " +
						"WHERE post_id = ? " +
						"GROUP BY image_id " +
						"LIMIT 1;",
				new Object[]{postId},
				JOB_POST_IMAGE_ROW_MAPPER
		);
	}
}
