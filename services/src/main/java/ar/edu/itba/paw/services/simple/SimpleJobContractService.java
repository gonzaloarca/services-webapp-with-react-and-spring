package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.models.exceptions.JobContractNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Transactional
@Service
public class SimpleJobContractService implements JobContractService {
    private static final String TYPE_OFFERED_STRING = "offered";
    private static final String TYPE_HIRED_STRING = "hired";
    private static final String STATE_ACTIVE_STRING = "active";
    private static final String STATE_PENDING_STRING = "pending";
    private static final String STATE_FINALIZED_STRING = "finalized";

    @Autowired
    private JobContractDao jobContractDao;

    @Autowired
    private JobCardService jobCardService;

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MailingService mailingService;

    @Override
    public List<JobContractCard> findContracts(Long userId, String contractState, String type, int page) {

        if (userId == null && contractState == null && type == null)
            return getJobContractCards(jobContractDao.findAll(page));

        if (userId == null || contractState == null || type == null ||
                (!contractState.equalsIgnoreCase(STATE_ACTIVE_STRING) && !contractState.equalsIgnoreCase(STATE_PENDING_STRING)
                        && !contractState.equalsIgnoreCase(STATE_FINALIZED_STRING)))
            throw new IllegalArgumentException("Bad parameters");

        List<JobContract.ContractState> states = getContractStates(contractState);
        if (type.equalsIgnoreCase(TYPE_OFFERED_STRING)) {
            return getJobContractCards(findByProIdAndSortedByModificationDateWithImage(userId, states, page));
        } else if (type.equalsIgnoreCase(TYPE_HIRED_STRING)) {
            return getJobContractCards(findByClientIdAndSortedByModificationDateWithImage(userId, states, page));
        } else
            throw new IllegalArgumentException("Invalid type param");
    }

    @Override
    public int findContractsMaxPage(Long userId, String contractState, String type) {

        if (userId == null && contractState == null && type == null)
            return jobContractDao.findAllMaxPage();

        if (userId == null || contractState == null || type == null ||
                (!contractState.equalsIgnoreCase(STATE_ACTIVE_STRING) && !contractState.equalsIgnoreCase(STATE_PENDING_STRING) && !contractState.equalsIgnoreCase(STATE_FINALIZED_STRING)))
            throw new IllegalArgumentException();

        if (type.equalsIgnoreCase(TYPE_OFFERED_STRING)) {
            return findContractsByProIdMaxPage(userId, getContractStates(contractState));
        } else if (type.equalsIgnoreCase(TYPE_HIRED_STRING)) {
            return findContractsByClientIdMaxPage(userId, getContractStates(contractState));
        } else
            throw new IllegalArgumentException("Invalid type param");
    }

    @Override
    public JobContractWithImage create(long clientId, long packageId, String description, String scheduledDate, Locale locale, String webpageUrl) {
        LocalDateTime parsedDate = LocalDateTime.parse(scheduledDate, DateTimeFormatter.ISO_DATE_TIME);
        if (jobPackageService.findByOnlyId(packageId).getJobPost().getUser().getId() == clientId)
            throw new IllegalArgumentException("Cannot create contract to self");
        JobContractWithImage jobContract = jobContractDao.create(clientId, packageId, description, parsedDate);
        mailingService.sendContractEmail(jobContract, locale, webpageUrl);
        return jobContract;
    }

    @Override
    public JobContractCard findContractCardById(long contractId) {
        JobContractWithImage jobContract = jobContractDao.findById(contractId).orElseThrow(JobContractNotFoundException::new);
        return new JobContractCard(jobContract,
                jobCardService.findByPostIdWithInactive(jobContract.getJobPackage().getJobPost().getId()),
                reviewService.findContractReview(jobContract.getId()).orElse(null), jobContract.getScheduledDate().toString());
    }

    @Override
    public List<JobContractWithImage> findByClientIdAndSortedByModificationDateWithImage(long id, List<JobContract.ContractState> states,
                                                                                         int page) {
        return jobContractDao.findByClientId(id, states, page);
    }

    @Override
    public List<JobContractWithImage> findByProIdAndSortedByModificationDateWithImage(long id, List<JobContract.ContractState> states,
                                                                                      int page) {
        return jobContractDao.findByProId(id, states, page);
    }

    @Override
    public int findCompletedContractsByProIdQuantity(long id) {
        return jobContractDao.findCompletedContractsByProIdQuantity(id);
    }

    @Override
    public int findContractsByClientIdMaxPage(long id, List<JobContract.ContractState> states) {
        return jobContractDao.findContractsByClientIdMaxPage(id, states);
    }

    @Override
    public int findContractsByProIdMaxPage(long id, List<JobContract.ContractState> states) {
        return jobContractDao.findContractsByProIdMaxPage(id, states);
    }

    private List<JobContractCard> getJobContractCards(List<JobContractWithImage> jobContracts) {
        List<JobContractCard> jobContractCards = new ArrayList<>();

        jobContracts.
                forEach(jobContract ->
                                jobContractCards.add(
                                        new JobContractCard(jobContract,
                                                jobCardService.findByPostIdWithInactive(jobContract.getJobPackage().getJobPost().getId()),
                                                reviewService.findContractReview(jobContract.getId()).orElse(null), jobContract.getScheduledDate().toString())
                                )
                        //puede no tener una review
                );
        return jobContractCards;
    }

    @Override
    public void changeContractState(long id, long userId, JobContract.ContractState state, Locale locale, String webPageUrl) {
        JobContractWithImage maybeContract = jobContractDao.findById(id).orElseThrow(JobContractNotFoundException::new);

        JobContract.ContractState currentState = maybeContract.getState();

        // No debería ser modificado si ya habia finalizado
        if (currentState == JobContract.ContractState.CLIENT_CANCELLED || currentState == JobContract.ContractState.PRO_CANCELLED
                || currentState == JobContract.ContractState.CLIENT_REJECTED || currentState == JobContract.ContractState.PRO_REJECTED ||
                currentState == JobContract.ContractState.COMPLETED)
            throw new IllegalStateException();
        boolean modifiedState = state.equals(JobContract.ContractState.PRO_MODIFIED)
                || state.equals(JobContract.ContractState.CLIENT_MODIFIED);
        if (!maybeContract.wasRescheduled()
                && modifiedState)
            jobContractDao.setWasRescheduled(id);
        else if (maybeContract.wasRescheduled() && modifiedState)
            throw new IllegalArgumentException("Cannot reschedule more than once");
        jobContractDao.changeContractState(id, userId, state);
        JobContractWithImage jobContract = findJobContractWithImage(id);
        JobPackage jobPackage = jobPackageService.findById(jobContract.getJobPackage().getId(), jobContract.getJobPackage().getJobPost().getId());
        JobPost jobPost = jobPostService.findById(jobPackage.getPostId());

        mailingService.sendUpdateContractStatusEmail(state, jobContract, jobPackage, jobPost, locale, webPageUrl);
    }

    @Override
    public void changeContractScheduledDate(long id, String scheduledDate, Locale locale) {
        LocalDateTime parsedDate = LocalDateTime.parse(scheduledDate, DateTimeFormatter.ISO_DATE_TIME);
        jobContractDao.changeContractScheduledDate(id, parsedDate);
    }

    @Override
    public JobContractWithImage findJobContractWithImage(long id) {
        return jobContractDao.findById(id).orElseThrow(JobContractNotFoundException::new);
    }

    @Override
    public ByteImage findImageByContractId(long contractId) {
        return jobContractDao.findImageByContractId(contractId).orElseThrow(ImageNotFoundException::new);
    }

    @Override
    public List<JobContract.ContractState> getContractStates(String contractState) {
        List<JobContract.ContractState> states = new ArrayList<>();

        switch (contractState) {
            case STATE_ACTIVE_STRING:
                states.add(JobContract.ContractState.APPROVED);
                break;
            case STATE_PENDING_STRING:
                states.add(JobContract.ContractState.PENDING_APPROVAL);
                states.add(JobContract.ContractState.PRO_MODIFIED);
                states.add(JobContract.ContractState.CLIENT_MODIFIED);
                break;
            case STATE_FINALIZED_STRING:
                states.add(JobContract.ContractState.COMPLETED);
                states.add(JobContract.ContractState.PRO_CANCELLED);
                states.add(JobContract.ContractState.PRO_REJECTED);
                states.add(JobContract.ContractState.CLIENT_CANCELLED);
                states.add(JobContract.ContractState.CLIENT_REJECTED);
                break;
        }

        return states;
    }

    @Override
    public JobContract findByIdWithUser(long id) {
        return jobContractDao.findByIdWithUser(id).orElseThrow(JobContractNotFoundException::new);
    }

    @Override
    public long addContractImage(long contractId, ByteImage contractImage) {
        return jobContractDao.addContractImage(contractId, contractImage);
    }

}
