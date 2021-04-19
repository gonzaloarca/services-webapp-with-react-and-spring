package login;

import ar.edu.itba.paw.interfaces.dao.JobPostImageDao;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPostImage;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.login.SimpleJobPostImageService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO: reescribir este test
@RunWith(MockitoJUnitRunner.class)
public class SimpleJobPostImageServiceTest {
	/*
	private static final long POST_ID = 20;
	private static final long FAKE_ID = 50;

	private static final User PROFESSIONAL = new User(
			8, "franquesada@gmail.com", "Francisco Quesada", "", "0800111333", false, true);
	private static final JobPost JOB_POST = new JobPost(
			POST_ID, PROFESSIONAL, "Plomero matriculado", "Lunes - Jueves de 09 a 16hrs", JobPost.JobType.PLUMBING,
			Arrays.asList(JobPost.Zone.BELGRANO, JobPost.Zone.PALERMO),0 ,true);
	private static final byte[] imageData1 = {1,2,3,4,5,6};
	private static final byte[] imageData2 = {7,4,1,2,5,8};
	private static final JobPostImage image1 = new JobPostImage(1, POST_ID, "123456", "image/jpg");
	private static final JobPostImage image2 = new JobPostImage(2, POST_ID, "741258", "image/png");
	private static final List<byte[]> BYTE_IMAGES = new ArrayList<>();
	private static final List<JobPostImage> IMAGE_LIST = new ArrayList<>();

	private void addValues() {
		BYTE_IMAGES.add(imageData1);
		BYTE_IMAGES.add(imageData2);
		IMAGE_LIST.add(image1);
		IMAGE_LIST.add(image2);
	}

	@InjectMocks
	private final SimpleJobPostImageService jobPostImageService = new SimpleJobPostImageService();

	@Mock
	private JobPostImageDao jobPostImageDao;

	@Mock
	private JobPostService jobPostService;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void addImagesSuccess() {
		addValues();

		Mockito.when(jobPostService.findById(Mockito.eq(POST_ID)))
				.thenReturn(JOB_POST);

		Mockito.when(jobPostImageDao.addImages(POST_ID, BYTE_IMAGES))
				.thenReturn(IMAGE_LIST);

		List<JobPostImage> out = jobPostImageService.addImages(POST_ID, BYTE_IMAGES);

		Assert.assertNotNull(out);
		Assert.assertEquals(IMAGE_LIST, out);
	}

	@Test
	public void addImagesException() {
		exceptionRule.expect(RuntimeException.class);

		Mockito.when(jobPostService.findById(Mockito.eq(FAKE_ID)))
				.thenThrow(RuntimeException.class);

		jobPostImageService.addImages(FAKE_ID, null);
	}

	@Test
	public void findByPostIdSuccess() {
		addValues();

		Mockito.when(jobPostImageDao.findByPostId(Mockito.eq(POST_ID)))
				.thenReturn(IMAGE_LIST);

		List<JobPostImage> out = jobPostImageService.findByPostId(POST_ID);

		Assert.assertNotNull(out);
		Assert.assertEquals(IMAGE_LIST, out);
	}

	@Test
	public void findByPostIdEmpty() {
		Mockito.when(jobPostImageDao.findByPostId(Mockito.eq(FAKE_ID)))
				.thenReturn(IMAGE_LIST);

		List<JobPostImage> out = jobPostImageService.findByPostId(POST_ID);

		Assert.assertNotNull(out);
		Assert.assertTrue(out.isEmpty());
	}
*/
}
