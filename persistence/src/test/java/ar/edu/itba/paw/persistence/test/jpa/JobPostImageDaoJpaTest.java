package ar.edu.itba.paw.persistence.test.jpa;

import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPostImage;
import ar.edu.itba.paw.persistence.jpa.JobPostImageDaoJpa;
import ar.edu.itba.paw.persistence.test.config.TestConfig;
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
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

	@PersistenceContext
	EntityManager em;

	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
	}

	private static final byte[] BYTES = {1,2,3,4,5,6};

	private static final String TYPE = "png";

	private static final ByteImage BYTE_IMAGE = new ByteImage(BYTES,TYPE);

	private static final ByteImage EXISTING_IMAGE = new ByteImage(BYTES,TYPE);

	@Test
	public void testAddImage() {
		long postId = 1;

		JobPostImage jobPostImage = jobPostImageDaoJpa.addImage(postId, BYTE_IMAGE);
		em.flush();
		Assert.assertNotNull(jobPostImage);
		Assert.assertEquals(postId, jobPostImage.getJobPost().getId());
		Assert.assertArrayEquals(BYTES,jobPostImage.getByteImage().getData());
		Assert.assertEquals(TYPE,jobPostImage.getByteImage().getType());
		int qty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"post_image","post_id = " + postId);
		Assert.assertEquals(qty,3);
	}

	@Test
	public void testFindById() {
		long imageId = 1;
		Optional<JobPostImage> jobPostImage = jobPostImageDaoJpa.findById(imageId);
		Assert.assertNotNull(jobPostImage);
		Assert.assertTrue(jobPostImage.isPresent());
		Assert.assertEquals(EXISTING_IMAGE,jobPostImage.get().getByteImage());
	}

	@Test
	public void testFindByIdWithNonExistentId() {
		long imageId = 999;
		Optional<JobPostImage> jobPostImage = jobPostImageDaoJpa.findById(imageId);
		Assert.assertNotNull(jobPostImage);
		Assert.assertFalse(jobPostImage.isPresent());
	}

	@Test
	public void testGetImagesIdsByPostId(){
		long postId = 1;
		List<Long> expected = Arrays.asList(1L,2L);
		List<Long> ids = jobPostImageDaoJpa.getImagesIdsByPostId(postId);
		Assert.assertFalse(ids.isEmpty());
		Assert.assertEquals(expected,ids);
	}

	@Test
	public void testGetImagesIdsByPostIdWithoutImages(){
		long postId = 2;
		List<Long> ids = jobPostImageDaoJpa.getImagesIdsByPostId(postId);
		Assert.assertTrue(ids.isEmpty());
	}

	@Test
	public void testGetImagesIdsByPostIdWithNonExistentId(){
		long postId = 999;
		List<Long> ids = jobPostImageDaoJpa.getImagesIdsByPostId(postId);
		Assert.assertTrue(ids.isEmpty());
	}

	@Test
	public void testGetImageCount(){
		long postId = 1;
		int realQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"post_image", "post_id = " + postId);
		int qty = jobPostImageDaoJpa.getImageCount(postId);
		Assert.assertEquals(realQty,qty);
	}

	@Test
	public void testGetImageCountWithoutImages(){
		long postId = 2;
		int realQty = 0;
		int qty = jobPostImageDaoJpa.getImageCount(postId);
		Assert.assertEquals(realQty,qty);
	}

	@Test
	public void testGetImageCountWithNonExistentId(){
		long postId = 999;
		long realQty = 0;
		int qty = jobPostImageDaoJpa.getImageCount(postId);
		Assert.assertEquals(realQty,qty);
	}
}
