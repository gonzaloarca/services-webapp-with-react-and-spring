package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;

import java.util.List;

public interface JobPackageService {

    JobPackage create(long postId, String title, String description, String price, long rateType);

    JobPackage findById(long id);

    List<JobPackage> findByPostId(long id);

    List<JobPackage> findByPostId(long id, int page);

    JobPost findPostByPackageId(long id);

    boolean updateJobPackage(long id, String title, String description, String price, int rateType);

    boolean deleteJobPackage(long id);

    JobPackage findByIdWithJobPost(int id);
}
