package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.dao.JobPostImageDao;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.EncodedImage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPostImage;
import ar.edu.itba.paw.persistence.utils.ImageDataConverter;
import exceptions.JobPostNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JobPostImageDaoJpa implements JobPostImageDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public JobPostImage addImage(long postId, ByteImage image) {
        JobPost jobPost = em.find(JobPost.class, postId);
        if(jobPost == null)
            throw new JobPostNotFoundException();

        EncodedImage encodedImage = new EncodedImage(ImageDataConverter.getEncodedString(image.getData()), image.getType());
        JobPostImage jobPostImage = new JobPostImage(jobPost, image, encodedImage);
        em.persist(jobPostImage);
        return jobPostImage;
    }

    @Override
    public List<JobPostImage> findImages(long postId) {
        List<JobPostImage> results = em.createQuery("FROM JobPostImage AS image WHERE image.jobPost.id = :id", JobPostImage.class)
                .setParameter("id", postId).getResultList();

        results.forEach(jobPostImage ->
                jobPostImage.setImage(new EncodedImage(
                        ImageDataConverter.getEncodedString(jobPostImage.getByteImage().getData()),
                        jobPostImage.getByteImage().getType()
                ))
        );

        return results;
    }

    @Override
    public int getImageCount(long postId) {
        return em.createQuery("SELECT COUNT(*) FROM JobPostImage AS image WHERE image.jobPost.id = :id", Long.class)
                .setParameter("id", postId).getResultList().stream().findFirst().orElse(0L).intValue();
    }
}
