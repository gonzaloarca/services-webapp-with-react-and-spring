package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobContract;

import java.util.List;
import java.util.Optional;

public interface JobContractService {

    JobContract create(long packageId, String description, String client_email, String client_username,
                       String client_phone);

    JobContract create(long packageId, String description, String client_email, String client_username,
                       String client_phone, byte[] imageData);

    Optional<JobContract> findById(long id);

    Optional<List<JobContract>> findByClientId(long id);

    Optional<List<JobContract>> findByProId(long id);

    Optional<List<JobContract>> findByPostId(long id);

    Optional<List<JobContract>> findByPackageId(long id);

    int findContractsQuantityByProId(long id);
}
