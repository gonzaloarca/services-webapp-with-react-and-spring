package simple;

import ar.edu.itba.paw.interfaces.dao.JobPostImageDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.simple.SimpleJobPostImageService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SimpleJobPostImageServiceTest {
	private static final long POST_ID = 20;
	private static final long FAKE_ID = 50;

	private static final List<JobPost.Zone> ZONES =
			new ArrayList<JobPost.Zone>(Arrays.asList(JobPost.Zone.values()[1],
					JobPost.Zone.values()[2]));
	private final User PROFESSIONAL = new User(
			8, "franquesada@gmail.com", "Francisco Quesada", "0800111333", true, true, LocalDateTime.now());
	private final JobPost JOB_POST = new JobPost(
			POST_ID, PROFESSIONAL, "Plomero matriculado", "Lunes - Jueves de 09 a 16hrs", JobPost.JobType.PLUMBING,
			ZONES, true, LocalDateTime.now());

	private final byte[] image1Bytes = {1,2,3,4,5};
	private final String image1Type = "image/png";
	private final ByteImage byteImage1 = new ByteImage(image1Bytes, image1Type);
	private final EncodedImage encodedImage1 = new EncodedImage(Base64.getEncoder().encodeToString(image1Bytes), image1Type);

	private final byte[] image2Bytes = {6,7,8,9,0};
	private final String image2Type = "image/jpg";
	private final ByteImage byteImage2 = new ByteImage(image2Bytes, image2Type);
	private final EncodedImage encodedImage2 = new EncodedImage(Base64.getEncoder().encodeToString(image2Bytes), image2Type);

	private final List<JobPostImage> jobPostImages = new ArrayList<>();

	@InjectMocks
	private final SimpleJobPostImageService jobPostImageService = new SimpleJobPostImageService();

	@Mock
	private JobPostImageDao jobPostImageDao;

	@Mock
	private ImageService imageService;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void addImagesSuccess() {
		Mockito.when(imageService.isValidImage(Mockito.any()))
				.thenReturn(true);

		Mockito.when(jobPostImageDao.addImage(POST_ID, byteImage1))
				.thenReturn(new JobPostImage(1, JOB_POST, encodedImage1));

		Mockito.when(jobPostImageDao.addImage(POST_ID, byteImage2))
				.thenReturn(new JobPostImage(2, JOB_POST, encodedImage2));

		List<ByteImage> images = new ArrayList<>();
		images.add(byteImage1);
		images.add(byteImage2);

		List<JobPostImage> out = jobPostImageService.addImages(POST_ID, images);

		Assert.assertNotNull(out);
		Assert.assertFalse(out.isEmpty());
		Assert.assertEquals(out.get(0).getImage(), encodedImage1);
		Assert.assertEquals(out.get(1).getImage(), encodedImage2);
	}

	@Test
	public void addImageException() {
		exceptionRule.expect(RuntimeException.class);

		jobPostImageService.addImage(FAKE_ID, byteImage1);
	}

	@Test
	public void findByPostIdSuccess() {
		jobPostImages.add(new JobPostImage(1, JOB_POST, encodedImage1));
		jobPostImages.add(new JobPostImage(2, JOB_POST, encodedImage2));

		Mockito.when(jobPostImageDao.findImages(Mockito.eq(POST_ID)))
				.thenReturn(jobPostImages);

		List<JobPostImage> out = jobPostImageService.findImages(POST_ID);

		Assert.assertNotNull(out);
		Assert.assertEquals(jobPostImages, out);
	}

	@Test
	public void findByPostIdEmpty() {
		Mockito.when(jobPostImageDao.findImages(Mockito.eq(FAKE_ID)))
				.thenReturn(jobPostImages);

		List<JobPostImage> out = jobPostImageService.findImages(FAKE_ID);

		Assert.assertNotNull(out);
		Assert.assertTrue(out.isEmpty());
	}

	@Test
	public void maxImagesReached() {
		Mockito.when(imageService.isValidImage(Mockito.any()))
				.thenReturn(true);

		Mockito.when(jobPostImageDao.getImageCount(POST_ID))
				.thenReturn(5);

		Assert.assertNull(jobPostImageService.addImage(POST_ID, byteImage1));
	}
}
