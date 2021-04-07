package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobPackage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface JobPackageService {

    JobPackage create(long postId, String title, String description, double price, JobPackage.RateType rateType);

    Optional<JobPackage> findById(long id);

    Optional<List<JobPackage>> findByPostId(long id);
}
