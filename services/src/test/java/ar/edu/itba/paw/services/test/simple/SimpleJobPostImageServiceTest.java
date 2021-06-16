package ar.edu.itba.paw.services.test.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobPostImageDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.JobPostImageNotFoundException;
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
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleJobPostImageServiceTest {
	private static final long POST_ID = 20;
	private static final long FAKE_ID = 50;

	private static final List<JobPost.Zone> ZONES =
            new ArrayList<>(Arrays.asList(JobPost.Zone.values()[1],
                    JobPost.Zone.values()[2]));

	private final User PROFESSIONAL = new User(
			8, "franquesada@gmail.com", "Francisco Quesada", "0800111333", true, true, LocalDateTime.now());

	private final JobPost JOB_POST = new JobPost(
			POST_ID, PROFESSIONAL, "Plomero matriculado", "Lunes - Jueves de 09 a 16hrs", JobPost.JobType.PLUMBING,
			ZONES, true, LocalDateTime.now());

	private final byte[] image1Bytes = {1,2,3,4,5};
	private final String image1Type = "image/png";
	private final ByteImage byteImage1 = new ByteImage(image1Bytes, image1Type);
	private final long IMAGE1_ID = 1;
	private final JobPostImage postImage1 = new JobPostImage(IMAGE1_ID, JOB_POST, byteImage1);

	private final byte[] image2Bytes = {6,7,8,9,0};
	private final String image2Type = "image/jpg";
	private final ByteImage byteImage2 = new ByteImage(image2Bytes, image2Type);
	private final long IMAGE2_ID = 2;
	private final JobPostImage postImage2 = new JobPostImage(IMAGE2_ID, JOB_POST, byteImage2);

	@InjectMocks
	private final SimpleJobPostImageService jobPostImageService = new SimpleJobPostImageService();

	@Mock
	private JobPostImageDao jobPostImageDao;

	@Mock
	private ImageService imageService;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
    public void addImageSuccessTest() {
		SimpleJobPostImageService spy = Mockito.spy(jobPostImageService);

	    Mockito.when(imageService.isValidImage(Mockito.any()))
                .thenReturn(true);
		Mockito.when(jobPostImageDao.addImage(Mockito.eq(POST_ID), Mockito.eq(byteImage1)))
				.thenReturn(postImage1);

		Mockito.doReturn(false).when(spy).maxImagesUploaded(POST_ID);

        JobPostImage result = spy.addImage(POST_ID, byteImage1);

        Assert.assertNotNull(result);
        Assert.assertEquals(IMAGE1_ID, result.getImageId());
        Assert.assertEquals(JOB_POST, result.getJobPost());
        Assert.assertEquals(byteImage1, result.getByteImage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addImageInvalidTest() {
        Mockito.when(imageService.isValidImage(Mockito.any()))
                .thenReturn(false);

        jobPostImageService.addImage(POST_ID, byteImage1);
    }

    @Test
    public void addImageMaxReachedTest() {
		SimpleJobPostImageService spy = Mockito.spy(jobPostImageService);

        Mockito.when(imageService.isValidImage(Mockito.any()))
                .thenReturn(true);

		Mockito.doReturn(true).when(spy).maxImagesUploaded(POST_ID);

        JobPostImage result = spy.addImage(POST_ID, byteImage1);

        Assert.assertNull(result);
    }

    @Test
	public void addImagesSuccessTest() {
		SimpleJobPostImageService spy = Mockito.spy(jobPostImageService);

		List<ByteImage> byteImageList = new ArrayList<>();
		byteImageList.add(byteImage1);
		byteImageList.add(byteImage2);

		List<JobPostImage> jobPostImageList = new ArrayList<>();
		jobPostImageList.add(postImage1);
		jobPostImageList.add(postImage2);

		Mockito.when(imageService.isValidImage(Mockito.any()))
				.thenReturn(true);
		Mockito.doReturn(false)
				.when(spy).maxImagesUploaded(POST_ID);
		Mockito.when(jobPostImageDao.addImage(Mockito.eq(POST_ID), Mockito.eq(byteImage1)))
				.thenReturn(postImage1);
		Mockito.when(jobPostImageDao.addImage(Mockito.eq(POST_ID), Mockito.eq(byteImage2)))
				.thenReturn(postImage2);

		List<JobPostImage> result = spy.addImages(POST_ID, byteImageList);

		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
		Assert.assertTrue(result.containsAll(jobPostImageList));
	}

	@Test(expected = IllegalArgumentException.class)
	public void addImagesInvalidTest() {
		List<ByteImage> byteImageList = new ArrayList<>();
		byteImageList.add(byteImage1);
		byteImageList.add(byteImage2);

		Mockito.when(imageService.isValidImage(Mockito.any()))
				.thenReturn(false);

		jobPostImageService.addImages(POST_ID, byteImageList);
	}

	@Test
	public void addImagesMaxReachedTest() {
		SimpleJobPostImageService spy = Mockito.spy(jobPostImageService);

		List<ByteImage> byteImageList = new ArrayList<>();
		byteImageList.add(byteImage1);
		byteImageList.add(byteImage2);

		Mockito.when(imageService.isValidImage(Mockito.any()))
				.thenReturn(true);
		Mockito.doReturn(true)
				.when(spy).maxImagesUploaded(POST_ID);

		List<JobPostImage> result = spy.addImages(POST_ID, byteImageList);

		Assert.assertNotNull(result);
		Assert.assertTrue(result.isEmpty());
	}

	@Test
	public void maxImagesNotReachedTest() {
		Mockito.when(jobPostImageDao.getImageCount(Mockito.eq(POST_ID)))
				.thenReturn(0);

		Assert.assertFalse(jobPostImageService.maxImagesUploaded(POST_ID));
	}

	@Test
	public void maxImagesReachedTest() {
		Mockito.when(jobPostImageDao.getImageCount(Mockito.eq(POST_ID)))
				.thenReturn(HirenetUtils.MAX_IMAGES_NUMBER);

		Assert.assertTrue(jobPostImageService.maxImagesUploaded(POST_ID));
	}

	@Test
	public void findByIdSuccessTest() {
		Mockito.when(jobPostImageDao.findById(Mockito.eq(IMAGE1_ID)))
				.thenReturn(Optional.of(postImage1));

		JobPostImage result = jobPostImageService.findById(IMAGE1_ID);

		Assert.assertNotNull(result);
		Assert.assertEquals(postImage1, result);
	}

	@Test(expected = JobPostImageNotFoundException.class)
	public void findByIdInvalidTest() {
		Mockito.when(jobPostImageDao.findById(Mockito.eq(FAKE_ID)))
				.thenReturn(Optional.empty());

		jobPostImageService.findById(FAKE_ID);
	}

	@Test
	public void getImagesIdsByPostIdSuccessTest() {
		List<Long> imageIdList = new ArrayList<>();
		imageIdList.add(IMAGE1_ID);
		imageIdList.add(IMAGE2_ID);

		Mockito.when(jobPostImageDao.getImagesIdsByPostId(Mockito.eq(POST_ID)))
				.thenReturn(imageIdList);

		List<Long> result = jobPostImageService.getImagesIdsByPostId(POST_ID);

		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
		Assert.assertTrue(result.containsAll(imageIdList));
	}

	@Test
	public void getImagesIdsByPostIdInvalidTest() {
		Mockito.when(jobPostImageDao.getImagesIdsByPostId(Mockito.eq(FAKE_ID)))
				.thenReturn(new ArrayList<>());

		List<Long> result = jobPostImageService.getImagesIdsByPostId(FAKE_ID);

		Assert.assertNotNull(result);
		Assert.assertTrue(result.isEmpty());
	}

}
