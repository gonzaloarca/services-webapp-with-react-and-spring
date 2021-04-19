package ar.edu.itba.paw.services.login;

import ar.edu.itba.paw.interfaces.dao.JobPostImageDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.JobPostImageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPostImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleJobPostImageService implements JobPostImageService {

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

		return jobPostImageDao.addImage(postId, image);
	}

	//	@Override
//	public List<JobPostImage> addImages(long postId, List<byte[]> byteImages) {
//		jobPostService.findById(postId);			//TODO: ver si hay una mejor forma de checkear si existe
//		return jobPostImageDao.addImages(postId, byteImages);
//	}

	@Override
	public List<JobPostImage> findByPostId(long postId) {
		return jobPostImageDao.findByPostId(postId);
	}
}
