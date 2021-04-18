package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface JobContractDao {

    JobContract create(long clientId, long packageId, String description);

    JobContract create(long clientId, long packageId, String description, byte[] imageData);

    Optional<JobContract> findById(long id);

    List<JobContract> findByClientId(long id);

    List<JobContract> findByProId(long id);

    List<JobContract> findByPostId(long id);

    List<JobContract> findByPackageId(long id);

    int findContractsQuantityByProId(long id);

    int findContractsQuantityByPostId(long id);

}
