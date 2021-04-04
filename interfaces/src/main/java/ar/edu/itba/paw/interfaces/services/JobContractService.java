package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobContract;

import java.util.List;
import java.util.Optional;

public interface JobContractService {

    JobContract create(long clientId, long packageId, String description);

    Optional<JobContract> findById(long id);

    Optional<List<JobContract>> findByClientId(long id);

    Optional<List<JobContract>> findByProId(long id);

    Optional<List<JobContract>> findByPostId(long id);

    Optional<List<JobContract>> findByPackageId(long id);

}
