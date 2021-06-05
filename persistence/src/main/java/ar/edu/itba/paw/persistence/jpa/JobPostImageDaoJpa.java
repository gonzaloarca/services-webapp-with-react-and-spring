package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.dao.JobPostImageDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.JobPostNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JobPostImageDaoJpa implements JobPostImageDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public JobPostImage addImage(long postId, ByteImage image) {
        JobPost jobPost = em.find(JobPost.class, postId);
        if(jobPost == null)
            throw new JobPostNotFoundException();

        JobPostImage jobPostImage = new JobPostImage(jobPost, image);
        em.persist(jobPostImage);
        return jobPostImage;
    }

    @Override
    public Optional<JobPostImage> findById(long imageId) {
        return Optional.ofNullable(em.find(JobPostImage.class, imageId));
    }

    @Override
    public List<Long> getImagesIdsByPostId(long postId) {
        return em.createQuery("SELECT image.imageId FROM JobPostImage AS image WHERE image.jobPost.id = :id", Long.class)
                .setParameter("id", postId).getResultList();
    }

    @Override
    public int getImageCount(long postId) {
        return em.createQuery("SELECT COUNT(*) FROM JobPostImage AS image WHERE image.jobPost.id = :id", Long.class)
                .setParameter("id", postId).getResultList().stream().findFirst().orElse(0L).intValue();
    }
}
