package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface JobContractDao {

    JobContract create(long clientId, long packageId, String description);

    JobContract create(long clientId, long packageId, String description, ByteImage image);

    Optional<JobContract> findById(long id);

    List<JobContract> findByClientId(long id, int page);

    List<JobContract> findByProId(long id, int page);

    List<JobContract> findByPostId(long id, int page);

    List<JobContract> findByPackageId(long id, int page);

    Optional<User> findClientByContractId(long id);

    Optional<User> findProByContractId(long id);

    int findContractsQuantityByProId(long id);

    int findContractsQuantityByPostId(long id);

    int findMaxPageContractsByClientId(long id);

    int findMaxPageContractsByProId(long id);

    void changeContractState(long id, JobContract.ContractState state);

}
