package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPostImage;

import java.util.List;

public interface JobPostImageService {

	JobPostImage addImage(long postId, ByteImage image);

//	List<JobPostImage> addImages(long postId, List<byte[]> byteImages);

	List<JobPostImage> findByPostId(long postId);
}
