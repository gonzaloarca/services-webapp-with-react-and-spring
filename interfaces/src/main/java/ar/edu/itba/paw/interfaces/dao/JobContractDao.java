package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobContractWithImage;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface JobContractDao {

    JobContractWithImage create(long clientId, long packageId, String description);

    JobContractWithImage create(long clientId, long packageId, String description, ByteImage image);

    Optional<JobContract> findById(long id);

    List<JobContract> findByClientId(long id, List<JobContract.ContractState> states, int page);

    List<JobContract> findByProId(long id, List<JobContract.ContractState> states, int page);

    List<JobContract> findByPostId(long id, int page);

    List<JobContract> findByPackageId(long id, int page);

    Optional<User> findClientByContractId(long id);

    int findContractsQuantityByProId(long id);

    int findContractsQuantityByPostId(long id);

    int findMaxPageContractsByClientId(long id, List<JobContract.ContractState> states);

    int findMaxPageContractsByProId(long id, List<JobContract.ContractState> states);

    void changeContractState(long id, JobContract.ContractState state);

    Optional<JobContractWithImage> findJobContractWithImage(long id);
}
