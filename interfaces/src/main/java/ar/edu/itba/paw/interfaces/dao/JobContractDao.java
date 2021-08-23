package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JobContractDao {

    List<JobContractWithImage> findAll(int page);

    JobContractWithImage create(long clientId, long packageId, String description, LocalDateTime scheduledDate);

    Optional<JobContractWithImage> findById(long id);

    List<JobContractWithImage> findByClientId(long id, List<JobContract.ContractState> states, int page);

    List<JobContractWithImage> findByProId(long id, List<JobContract.ContractState> states, int page);

    List<JobContract> findByPostId(long id, int page);

    List<JobContract> findByPackageId(long packageId, long postId, int page);

    Optional<User> findClientByContractId(long id);

    int findAllMaxPage();

    int findCompletedContractsByProIdQuantity(long id);

    int findContractsByPostIdQuantity(long id);

    int findContractsByClientIdMaxPage(long id, List<JobContract.ContractState> states);

    int findContractsByProIdMaxPage(long id, List<JobContract.ContractState> states);

    void changeContractState(long id, long userId, JobContract.ContractState state);

    void changeContractScheduledDate(long id, LocalDateTime dateTime);

    Optional<ByteImage> findImageByContractId(long contractId);

    Optional<JobContract> findByIdWithUser(long id);

    long addContractImage(long contractId,ByteImage contractImage);

    void setWasRescheduled(long id);
}
