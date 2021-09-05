package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public interface JobContractService {

    List<JobContractCard> findContracts(Long userId, String contractState, String type, int page);

    int findContractsMaxPage(Long userId, String contractState, String type);

    JobContractWithImage create(long clientId, long packageId, String description, String scheduledDate, Locale locale, String webpageUrl);

    JobContractCard findContractCardById(long contractId);

    List<JobContractWithImage> findByClientIdAndSortedByModificationDateWithImage(long id, List<JobContract.ContractState> states, int page);

    List<JobContractWithImage> findByProIdAndSortedByModificationDateWithImage(long id, List<JobContract.ContractState> states, int page);

    int findCompletedContractsByProIdQuantity(long id);

    int findContractsByClientIdMaxPage(long id, List<JobContract.ContractState> states);

    int findContractsByProIdMaxPage(long id, List<JobContract.ContractState> states);

    void changeContractState(long id, long userId, JobContract.ContractState state, Locale locale, String webPageUrl);

    void changeContractScheduledDate(long id, String scheduledDate, Locale locale);

    JobContractWithImage findJobContractWithImage(long id);

    ByteImage findImageByContractId(long contractId);

    List<JobContract.ContractState> getContractStates(String contractState);

    JobContract findByIdWithUser(long id);

    long addContractImage(long contractId, ByteImage contractImage);
}
