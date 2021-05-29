package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPostImage;

import java.util.List;
import java.util.Optional;

public interface JobPostImageDao {

    JobPostImage addImage(long postId, ByteImage image);

    Optional<JobPostImage> findById(long imageId);

    List<Long> getImagesIdsByPostId(long postId);

    int getImageCount(long postId);

}
