package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobContractWithImage;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JobContractDao {

    JobContractWithImage create(long clientId, long packageId, long postId, String description, LocalDateTime scheduledDate);

    Optional<JobContract> findById(long id);

    List<JobContract> findByClientId(long id, List<JobContract.ContractState> states, int page);

    List<JobContract> findByClientIdAndSortedByModificationDate(long id, List<JobContract.ContractState> states, int page);

    List<JobContract> findByProId(long id, List<JobContract.ContractState> states, int page);

    List<JobContract> findByProIdAndSortedByModificationDate(long id, List<JobContract.ContractState> states, int page);

    List<JobContract> findByPostId(long id, int page);

    List<JobContract> findByPackageId(long packageId, long postId, int page);

    Optional<User> findClientByContractId(long id);

    int findCompletedContractsByProIdQuantity(long id);

    int findContractsByPostIdQuantity(long id);

    int findContractsByClientIdMaxPage(long id, List<JobContract.ContractState> states);

    int findContractsByProIdMaxPage(long id, List<JobContract.ContractState> states);

    void changeContractState(long id, JobContract.ContractState state);

    void changeContractScheduledDate(long id, LocalDateTime dateTime);

    Optional<JobContractWithImage> findJobContractWithImage(long id);

    Optional<ByteImage> findImageByContractId(long contractId, long packageId, long postId);

    Optional<JobContract> findByIdWithUser(long id);

    int findByPackageIdMaxPage(long packageId, long postId);

    long addContractImage(long contractId,ByteImage contractImage);
}
