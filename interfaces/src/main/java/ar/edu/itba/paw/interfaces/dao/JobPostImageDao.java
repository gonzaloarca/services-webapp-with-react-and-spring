package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPostImage;
import java.util.List;

public interface JobPostImageDao {

	JobPostImage addImage(long postId, ByteImage image);

	List<JobPostImage> findImages(long postId);

	int getImageCount(long postId);

}
