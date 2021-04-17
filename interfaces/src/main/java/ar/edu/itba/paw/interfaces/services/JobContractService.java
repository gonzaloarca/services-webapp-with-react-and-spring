package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobContract;

import java.util.List;
import java.util.Optional;

public interface JobContractService {

    JobContract create(String client_email,long packageId, String description);

    JobContract create(String client_email,long packageId, String description, byte[] imageData);

    Optional<JobContract> findById(long id);

    List<JobContract> findByClientId(long id);

    List<JobContract> findByProId(long id);

    List<JobContract> findByPostId(long id);

    List<JobContract> findByPackageId(long id);

    int findContractsQuantityByProId(long id);
}
