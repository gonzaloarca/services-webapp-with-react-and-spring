package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public interface JobContractService {

    List<JobContract> findAll(int page);

    List<JobContractCard> findContracts(Long userId, String contractState, String role, int page, Locale locale);

    int findContractsMaxPage(Long userId, String contractState, String role, Locale locale);

    JobContractWithImage create(long clientId, long packageId, String description, LocalDateTime scheduledDate, Locale locale, String webpageUrl);

    JobContract findById(long contractId);

    List<JobContract> findByClientIdAndSortedByModificationDate(long id, List<JobContract.ContractState> states, int page);

    List<JobContract> findByProId(long id, List<JobContract.ContractState> states, int page);

    List<JobContract> findByProIdAndSortedByModificationDate(long id, List<JobContract.ContractState> states, int page);

    List<JobContract> findByPostId(long id);

    List<JobContract> findByPostId(long id, int page);

    User findClientByContractId(long id);

    int findAllMaxPage();

    int findCompletedContractsByProIdQuantity(long id);

    int findContractsByClientIdMaxPage(long id, List<JobContract.ContractState> states);

    int findContractsByProIdMaxPage(long id, List<JobContract.ContractState> states);

    void changeContractState(long id, JobContract.ContractState state, Locale locale, String webPageUrl);

    void changeContractScheduledDate(long id, LocalDateTime scheduledDate, boolean isServiceOwner, Locale locale);

    JobContractWithImage findJobContractWithImage(long id);

    ByteImage findImageByContractId(long contractId);

    List<JobContract.ContractState> getContractStates(String contractState);

    JobContract findByIdWithUser(long id);

    long addContractImage(long contractId, ByteImage contractImage);
}
