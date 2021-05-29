package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobContractCard;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface JobContractService {

    JobContract create(String client_email, long packageId, String description);

    JobContract create(String client_email, long packageId, String description, ByteImage image);

    JobContract findById(long id);

    List<JobContract> findByClientId(long id);

    List<JobContract> findByClientId(long id, int page);

    List<JobContract> findByClientId(long id, List<JobContract.ContractState> states, int page);

    List<JobContract> findByProId(long id);

    List<JobContract> findByProId(long id, int page);

    List<JobContract> findByProId(long id, List<JobContract.ContractState> states, int page);

    List<JobContract> findByPostId(long id);

    List<JobContract> findByPostId(long id, int page);

    List<JobContract> findByPackageId(long id);

    List<JobContract> findByPackageId(long id, int page);

    User findClientByContractId(long id);

    int findContractsQuantityByProId(long id);

    int findContractsQuantityByPostId(long id);

    int findMaxPageContractsByClientId(long id, List<JobContract.ContractState> states);

    int findMaxPageContractsByProId(long id, List<JobContract.ContractState> states);

    List<JobContractCard> findJobContractCardsByClientId(long id, List<JobContract.ContractState> states, int page);

    List<JobContractCard> findJobContractCardsByProId(long id, List<JobContract.ContractState> states, int page);

    void changeContractState(long id, JobContract.ContractState state);

}
