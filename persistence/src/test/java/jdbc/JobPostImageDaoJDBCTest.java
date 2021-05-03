package jdbc;

import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPostImage;
import ar.edu.itba.paw.persistence.jdbc.JobPostImageDaoJDBC;
import ar.edu.itba.paw.persistence.utils.ImageDataConverter;
import config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

//TODO hacer este test
@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:db_data_test.sql")
public class JobPostImageDaoJDBCTest {

	@InjectMocks
	@Autowired
	JobPostImageDaoJDBC jobPostImageDaoJDBC;

	@Autowired
	DataSource ds;

	JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
	}

//	TODO:FIX TESTS
	@Test
	public void addImageTest() {
		long post_id = 1;
		byte[] image_data = {1,2,3,4,5,6,7};
		String image_type = "image/png";

		JobPostImage jobPostImage = jobPostImageDaoJDBC.addImage(post_id, new ByteImage(image_data, image_type));

		Assert.assertNotNull(jobPostImage);
		Assert.assertEquals(post_id, jobPostImage.getPostId());
		Assert.assertEquals(ImageDataConverter.getEncodedString(image_data), jobPostImage.getImage().getString());
	}

	@Test
	public void addImagesTest(){
		long post_id = 1;
		byte[] first_image = {1,2,3,4,5,6,7};
		byte[] second_image = {8,9,1,2,3,4,5};

		List<byte[]> byteImages = new ArrayList<>();
		byteImages.add(first_image);
		byteImages.add(second_image);

		List<JobPostImage> images = new ArrayList<>();
		for(byte[] image : byteImages) {
			images.add(jobPostImageDaoJDBC.addImage(post_id, new ByteImage(image, "image/jpg")));
		}

		Assert.assertNotNull(images);
		Assert.assertFalse(images.isEmpty());

		for(int i = 0; i < images.size(); i++) {
			Assert.assertEquals(post_id, images.get(i).getPostId());
			Assert.assertEquals(ImageDataConverter.getEncodedString(byteImages.get(i)),
					images.get(i).getImage().getString());
		}

		images = jobPostImageDaoJDBC.findImages(post_id);

		for(int i = 0; i < images.size(); i++) {
			Assert.assertEquals(post_id, images.get(i).getPostId());
			Assert.assertEquals(ImageDataConverter.getEncodedString(byteImages.get(i)),
					images.get(i).getImage().getString());
		}
	}

}
