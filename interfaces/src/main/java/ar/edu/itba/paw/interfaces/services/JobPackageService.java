package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobPackage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface JobPackageService {

    JobPackage create(long postId, String title, String description, String price, int rateType);

    JobPackage findById(long id);

    List<JobPackage> findByPostId(long id);

    List<JobPackage> findByPostId(long id, int page);

    boolean updateJobPackage(long id, String title, String description, String price, int rateType);

    boolean deleteJobPackage(long id);
}
