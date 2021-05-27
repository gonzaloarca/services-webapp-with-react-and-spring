package jdbc;

import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPostImage;
import ar.edu.itba.paw.persistence.jpa.JobPostImageDaoJpa;
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
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:user_data_test.sql")
@Transactional
public class JobPostImageDaoJpaTest {

	@InjectMocks
	@Autowired
    JobPostImageDaoJpa jobPostImageDaoJpa;

	@Autowired
	DataSource ds;

	JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
	}

	@Test
	public void addImageTest() {
		long post_id = 1;
		byte[] image_data = {1,2,3,4,5,6,7};
		String image_type = "image/png";

		JobPostImage jobPostImage = jobPostImageDaoJpa.addImage(post_id, new ByteImage(image_data, image_type));

		Assert.assertNotNull(jobPostImage);
		Assert.assertEquals(post_id, jobPostImage.getJobPost().getId());
		Assert.assertEquals(ImageDataConverter.getEncodedString(image_data), jobPostImage.getImage().getString());
	}

	//TODO testear los otros metodos
}
