package ar.edu.itba.paw.services.login;

import ar.edu.itba.paw.interfaces.dao.JobPostImageDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.JobPostImageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPostImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleJobPostImageService implements JobPostImageService {

	private final int MAX_IMAGES_NUMBER = 5;

	@Autowired
	private JobPostImageDao jobPostImageDao;

	@Autowired
	private JobPostService jobPostService;

	@Autowired
	private ImageService imageService;

	@Override
	public JobPostImage addImage(long postId, ByteImage image) {
		jobPostService.findById(postId);			//TODO: ver si hay una mejor forma de checkear si existe

		if(!imageService.isValidImage(image))
			throw new RuntimeException("Invalid byte image");
		if(maxImagesUploaded(postId))
			return null;

		return jobPostImageDao.addImage(postId, image);
	}

	@Override
	public List<JobPostImage> addImages(long postId, List<ByteImage> byteImages) {
		jobPostService.findById(postId);			//TODO: ver si hay una mejor forma de checkear si existe
		List<JobPostImage> images = new ArrayList<>();
		for(ByteImage image : byteImages){
			if(!imageService.isValidImage(image))
				throw new RuntimeException("Invalid byte image");
			if(maxImagesUploaded(postId))
				break;
			images.add(jobPostImageDao.addImage(postId, image));
		}
		return images;
	}

	@Override
	public List<JobPostImage> findImages(long postId) {
		return jobPostImageDao.findImages(postId);
	}

	@Override
	public JobPostImage findPostImage(long postId) {
		jobPostService.findById(postId);			//TODO: ver si hay una mejor forma de checkear si existe
		return jobPostImageDao.findPostImage(postId);
	}

	@Override
	public boolean maxImagesUploaded(long postId) {
		return jobPostImageDao.getImageCount(postId) >= MAX_IMAGES_NUMBER;
	}
}
