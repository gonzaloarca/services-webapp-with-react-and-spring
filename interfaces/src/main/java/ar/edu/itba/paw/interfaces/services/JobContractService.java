package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface JobContractService {

    JobContractWithImage create(String client_email, long packageId, String description, String scheduledDate, Locale locale);

    JobContractWithImage create(String client_email, long packageId, String description, String scheduledDate, ByteImage image, Locale locale);

    JobContract findById(long id);

    List<JobContract> findByClientId(long id);

    List<JobContract> findByClientId(long id, int page);

    List<JobContract> findByClientId(long id, List<JobContract.ContractState> states, int page);

    List<JobContract> findByClientIdAndSortedByModificationDate(long id, List<JobContract.ContractState> states,
                                                                int page);

    List<JobContract> findByProId(long id);

    List<JobContract> findByProId(long id, int page);

    List<JobContract> findByProId(long id, List<JobContract.ContractState> states, int page);

    List<JobContract> findByProIdAndSortedByModificationDate(long id, List<JobContract.ContractState> states,
                                                             int page);

    List<JobContract> findByPostId(long id);

    List<JobContract> findByPostId(long id, int page);

    List<JobContract> findByPackageId(long id);

    List<JobContract> findByPackageId(long id, int page);

    User findClientByContractId(long id);

    int findCompletedContractsQuantityByProId(long id);

    int findContractsQuantityByPostId(long id);

    int findMaxPageContractsByClientId(long id, List<JobContract.ContractState> states);

    int findMaxPageContractsByProId(long id, List<JobContract.ContractState> states);

    List<JobContractCard> findJobContractCardsByProIdAndSorted(long id, List<JobContract.ContractState> states, int page, Locale locale);

    List<JobContractCard> findJobContractCardsByClientId(long id, List<JobContract.ContractState> states, int page, Locale locale);

    List<JobContractCard> findJobContractCardsByProId(long id, List<JobContract.ContractState> states, int page, Locale locale);

    List<JobContractCard> findJobContractCardsByClientIdAndSorted(long id, List<JobContract.ContractState> states, int page, Locale locale);

    void changeContractState(long id, JobContract.ContractState state);

    void changeContractScheduledDate(long id, String dateTime, boolean isServiceOwner, Locale locale);

    JobContractWithImage findJobContractWithImage(long id);

    ByteImage findImageByContractId(long id);

    List<JobContract.ContractState> getContractStates(String contractState);

    JobContract findByIdWithUser(long id);
}
