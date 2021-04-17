package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.JobPostImage;
import java.util.List;
import java.util.Optional;

public interface JobPostImageDao {

	JobPostImage addImage(long postId, byte[] byteImage);

	List<JobPostImage> addImages(long postId, List<byte[]> byteImages);

	Optional<List<JobPostImage>> findByPostId(long postId);

}
