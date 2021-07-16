package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.models.exceptions.JobContractNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Transactional
@Service
public class SimpleJobContractService implements JobContractService {

    @Autowired
    private JobContractDao jobContractDao;

    @Autowired
    private UserService userService;

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

    @Autowired
    PaginationService paginationService;

    @Autowired
    private MessageSource messageSource;

    @Override
    public List<JobContractCard> findContracts(Long userId, String contractState, String role, int page, Locale locale) {
        if (page < 1) page = 1;

        if (userId == null && contractState == null && role == null)
            return getJobContractCards(locale, jobContractDao.findAll(page));

        if (userId == null || contractState == null || role == null ||
                (!contractState.equals("active") && !contractState.equals("pending") && !contractState.equals("finalized")))
            throw new IllegalArgumentException();

        List<JobContract.ContractState> states = getContractStates(contractState);
        if (role.equals("professional")) {
            return getJobContractCards(locale, findByProIdAndSortedByModificationDate(userId, states, page - 1));
        } else if (role.equals("client")) {
            return getJobContractCards(locale, findByClientIdAndSortedByModificationDate(userId, states, page - 1));
        } else
            throw new IllegalArgumentException();
    }

    @Override
    public int findContractsMaxPage(Long userId, String contractState, String role, Locale locale) {

        if (userId == null && contractState == null && role == null)
            return jobContractDao.findAllMaxPage();

        if (userId == null || contractState == null || role == null ||
                (!contractState.equals("active") && !contractState.equals("pending") && !contractState.equals("finalized")))
            throw new IllegalArgumentException();

        if (role.equals("professional")) {
            return findContractsByProIdMaxPage(userId, getContractStates(contractState));
        } else if (role.equals("client")) {
            return findContractsByClientIdMaxPage(userId, getContractStates(contractState));
        } else
            throw new IllegalArgumentException();
    }

    @Override
    public List<JobContract> findAll(int page) {
        return jobContractDao.findAll(page);
    }

    @Override
    public JobContractWithImage create(long clientId, long packageId, String description, LocalDateTime scheduledDate, Locale locale, String webpageUrl) {
        JobContractWithImage jobContract = jobContractDao.create(clientId, packageId, description, scheduledDate);
        mailingService.sendContractEmail(jobContract, locale, webpageUrl);
        return jobContract;
    }

    @Override
    public JobContract findById(long contractId) {
        Optional<JobContract> jobContract = jobContractDao.findById(contractId);
        if (!jobContract.isPresent())
            throw new JobContractNotFoundException();
        return jobContract.get();
    }

    @Override
    public List<JobContract> findByClientIdAndSortedByModificationDate(long id, List<JobContract.ContractState> states,
                                                                       int page) {
        return jobContractDao.findByClientIdAndSortedByModificationDate(id, states, page);
    }

    @Override
    public List<JobContract> findByProId(long id, List<JobContract.ContractState> states, int page) {
        return jobContractDao.findByProId(id, states, page);
    }

    @Override
    public List<JobContract> findByProIdAndSortedByModificationDate(long id, List<JobContract.ContractState> states,
                                                                    int page) {
        return jobContractDao.findByProIdAndSortedByModificationDate(id, states, page);
    }

    @Override
    public List<JobContract> findByPostId(long id) {
        return jobContractDao.findByPostId(id, HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobContract> findByPostId(long id, int page) {
        return jobContractDao.findByPostId(id, page);
    }

    @Override
    public User findClientByContractId(long id) {
        return jobContractDao.findClientByContractId(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public int findCompletedContractsByProIdQuantity(long id) {
        return jobContractDao.findCompletedContractsByProIdQuantity(id);
    }

    @Override
    public int findAllMaxPage() {
        return jobContractDao.findAllMaxPage();
    }

    @Override
    public int findContractsByClientIdMaxPage(long id, List<JobContract.ContractState> states) {
        return jobContractDao.findContractsByClientIdMaxPage(id, states);
    }

    @Override
    public int findContractsByProIdMaxPage(long id, List<JobContract.ContractState> states) {
        return jobContractDao.findContractsByProIdMaxPage(id, states);
    }

    private List<JobContractCard> getJobContractCards(Locale locale, List<JobContract> jobContracts) {
        List<JobContractCard> jobContractCards = new ArrayList<>();

        jobContracts.
                forEach(jobContract ->
                                jobContractCards.add(
                                        new JobContractCard(jobContract,
                                                jobCardService.findByPostIdWithInactive(jobContract.getJobPackage().getJobPost().getId()),
                                                reviewService.findContractReview(jobContract.getId()).orElse(null), localDateTimeToString(jobContract.getScheduledDate(), locale))
                                )
                        //puede no tener una review
                );
        return jobContractCards;
    }


    @Override
    public void changeContractState(long id, JobContract.ContractState state, Locale locale, String webPageUrl) {
        Optional<JobContract> maybeContract = jobContractDao.findById(id);

        if (!maybeContract.isPresent())
            throw new JobContractNotFoundException();

        JobContract.ContractState currentState = maybeContract.get().getState();

        // No debería ser modificado si ya habia finalizado
        if (currentState == JobContract.ContractState.CLIENT_CANCELLED || currentState == JobContract.ContractState.PRO_CANCELLED
                || currentState == JobContract.ContractState.CLIENT_REJECTED || currentState == JobContract.ContractState.PRO_REJECTED ||
                currentState == JobContract.ContractState.COMPLETED)
            throw new IllegalStateException();

        jobContractDao.changeContractState(id, state);
        JobContractWithImage jobContract = findJobContractWithImage(id);
        JobPackage jobPackage = jobPackageService.findById(jobContract.getJobPackage().getId(), jobContract.getJobPackage().getJobPost().getId());
        JobPost jobPost = jobPostService.findById(jobPackage.getPostId());

        mailingService.sendUpdateContractStatusEmail(jobContract, jobPackage, jobPost, locale, webPageUrl);
    }

    @Override
    public void changeContractScheduledDate(long id, LocalDateTime scheduledDate, boolean isServiceOwner, Locale locale) {
        jobContractDao.changeContractScheduledDate(id, scheduledDate);
    }

    private String localDateTimeToString(LocalDateTime dateTime, Locale locale) {
        String datePattern = messageSource.getMessage("spring.mvc.format.date-time", null, locale);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);

        return formatter.format(dateTime);
    }

    @Override
    public JobContractWithImage findJobContractWithImage(long id) {
        return jobContractDao.findJobContractWithImage(id).orElseThrow(JobContractNotFoundException::new);
    }

    @Override
    public ByteImage findImageByContractId(long contractId) {
        return jobContractDao.findImageByContractId(contractId).orElseThrow(ImageNotFoundException::new);
    }

    @Override
    public List<JobContract.ContractState> getContractStates(String contractState) {
        List<JobContract.ContractState> states = new ArrayList<>();

        switch (contractState) {
            case "active":
                states.add(JobContract.ContractState.APPROVED);
                break;
            case "pending":
                states.add(JobContract.ContractState.PENDING_APPROVAL);
                states.add(JobContract.ContractState.PRO_MODIFIED);
                states.add(JobContract.ContractState.CLIENT_MODIFIED);
                break;
            case "finalized":
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
