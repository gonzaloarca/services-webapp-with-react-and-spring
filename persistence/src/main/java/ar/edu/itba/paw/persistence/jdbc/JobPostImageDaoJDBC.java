package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.interfaces.dao.JobPostImageDao;
import ar.edu.itba.paw.models.JobPostImage;
import ar.edu.itba.paw.persistence.utils.ImageDataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class JobPostImageDaoJDBC implements JobPostImageDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsertImage;

	private final static RowMapper<JobPostImage> JOB_POST_IMAGE_ROW_MAPPER = ((resultSet, i) -> new JobPostImage(
			resultSet.getLong("image_id"),
			resultSet.getLong("post_id"),
			ImageDataConverter.getEncodedString(resultSet.getBytes("image_data")),
			ImageDataConverter.getImageType(resultSet.getBytes("image_data"))
	));

	@Autowired
	public JobPostImageDaoJDBC(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsertImage = new SimpleJdbcInsert(ds).withTableName("post_image");
	}

	@Override
	public JobPostImage addImage(long postId, byte[] byteImage) {
		Number imageId = jdbcInsertImage.executeAndReturnKey(new HashMap<String, Object>() {{
			put("post_id", postId);
			put("image_data", byteImage);
		}});

		return new JobPostImage(imageId.longValue(), postId,
				ImageDataConverter.getEncodedString(byteImage), ImageDataConverter.getImageType(byteImage));
	}

	@Override
	public List<JobPostImage> addImages(long postId, List<byte[]> byteImages) {
		List<JobPostImage> images = new ArrayList<>();

		byteImages.forEach(image -> images.add(addImage(postId, image)));

		return images;
	}

	@Override
	public List<JobPostImage> findByPostId(long postId) {
		return jdbcTemplate.query(
			"SELECT image_id, post_id, image_data " +
					"FROM post_image " +
					"WHERE post_id = ?;",
				new Object[]{postId},
				JOB_POST_IMAGE_ROW_MAPPER
		);
	}
}
