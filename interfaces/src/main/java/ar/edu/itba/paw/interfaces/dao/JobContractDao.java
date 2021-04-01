package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.JobContract;

import java.util.List;
import java.util.Optional;

public interface JobContractDao {

    JobContract create(long postId, long clientId, long packageId, String description);

    Optional<JobContract> findById(long id);

    Optional<List<JobContract>> findByClientId(long id);

    Optional<List<JobContract>> findByProId(long id);

    Optional<List<JobContract>> findByPostId(long id);

}
