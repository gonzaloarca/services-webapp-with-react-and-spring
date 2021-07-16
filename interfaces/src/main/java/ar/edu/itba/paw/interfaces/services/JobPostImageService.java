package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPostImage;

import java.util.List;
import java.util.Optional;

public interface JobPostImageService {

	JobPostImage addImage(long postId, ByteImage image);

	List<JobPostImage> addImages(long postId, List<ByteImage> byteImages);

	JobPostImage findById(long imageId, long postId);

	List<Long> getImagesIdsByPostId(long postId);

	boolean maxImagesUploaded(long postId);
}
