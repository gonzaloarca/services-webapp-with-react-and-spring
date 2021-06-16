package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.models.exceptions.JobContractNotFoundException;
import ar.edu.itba.paw.models.exceptions.JobPackageNotFoundException;
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
    private ReviewService reviewService;

    @Autowired
    private MailingService mailingService;

    @Autowired
    private MessageSource messageSource;


    @Override
    public JobContractWithImage create(String clientEmail, long packageId, String description, String scheduledDate, Locale locale) {
        return create(clientEmail, packageId, description, scheduledDate, null, locale);
    }

    @Override
    public JobContractWithImage create(String clientEmail, long packageId, String description, String scheduledDate, ByteImage image, Locale locale) {

        User user = userService.findByEmail(clientEmail).orElseThrow(UserNotFoundException::new);
        JobContractWithImage jobContract;

        String datePattern = messageSource.getMessage("spring.mvc.format.date-time", null, locale);
        LocalDateTime parsedDate = LocalDateTime.parse(scheduledDate, DateTimeFormatter.ofPattern(datePattern));

        if (image == null)
            jobContract = jobContractDao.create(user.getId(), packageId, description,
                    parsedDate);
        else
            jobContract = jobContractDao.create(user.getId(), packageId, description, parsedDate, image);

        mailingService.sendContractEmail(jobContract, locale);

        return jobContract;
    }

    @Override
    public JobContract findById(long id) {
        return jobContractDao.findById(id).orElseThrow(JobPackageNotFoundException::new);
    }

    @Override
    public List<JobContract> findByClientId(long id) {
        return jobContractDao.findByClientId(id, Arrays.asList(JobContract.ContractState.values()), HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobContract> findByClientId(long id, List<JobContract.ContractState> states, int page) {
        return jobContractDao.findByClientId(id, states, page);
    }

    @Override
    public List<JobContract> findByClientId(long id, int page) {
        return jobContractDao.findByClientId(id, Arrays.asList(JobContract.ContractState.values()), page);
    }

    @Override
    public List<JobContract> findByClientIdAndSortedByModificationDate(long id, List<JobContract.ContractState> states,
                                                                       int page) {
        return jobContractDao.findByClientIdAndSortedByModificationDate(id, states, page);
    }

    @Override
    public List<JobContract> findByProId(long id) {
        return jobContractDao.findByProId(id, Arrays.asList(JobContract.ContractState.values()), HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobContract> findByProId(long id, int page) {
        return jobContractDao.findByProId(id, Arrays.asList(JobContract.ContractState.values()), page);
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
    public List<JobContract> findByPackageId(long id) {
        return jobContractDao.findByPackageId(id, HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobContract> findByPackageId(long id, int page) {
        return jobContractDao.findByPackageId(id, page);
    }

    @Override
    public User findClientByContractId(long id) {
        return jobContractDao.findClientByContractId(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public int findContractsQuantityByProId(long id) {
        return jobContractDao.findContractsQuantityByProId(id);
    }

    @Override
    public int findContractsQuantityByPostId(long id) {
        return jobContractDao.findContractsQuantityByPostId(id);
    }

    @Override
    public int findMaxPageContractsByClientId(long id, List<JobContract.ContractState> states) {
        return jobContractDao.findMaxPageContractsByClientId(id, states);
    }

    @Override
    public int findMaxPageContractsByProId(long id, List<JobContract.ContractState> states) {
        return jobContractDao.findMaxPageContractsByProId(id, states);
    }

    @Override
    public List<JobContractCard> findJobContractCardsByProId(long id, List<JobContract.ContractState> states, int page, Locale locale) {
        List<JobContractCard> jobContractCards = new ArrayList<>();

        findByProId(id, states, page)
                .forEach(jobContract ->
                                jobContractCards.add(
                                        new JobContractCard(jobContract, jobCardService
                                                .findByPostIdWithInactive(jobContract.getJobPackage().getPostId()),
                                                reviewService.findContractReview(jobContract.getId()).orElse(null), localDateTimeToString(jobContract.getScheduledDate(), locale)))
                        //puede no tener una review
                );

        return jobContractCards;
    }

    @Override
    public List<JobContractCard> findJobContractCardsByClientId(long id, List<JobContract.ContractState> states, int page, Locale locale) {
        List<JobContractCard> jobContractCards = new ArrayList<>();

        findByClientId(id, states, page).
                forEach(jobContract ->
                                jobContractCards.add(
                                        new JobContractCard(jobContract,
                                                jobCardService.findByPackageIdWithPackageInfoWithInactive(jobContract.getJobPackage().getId()),
                                                reviewService.findContractReview(jobContract.getId()).orElse(null), localDateTimeToString(jobContract.getScheduledDate(), locale))
                                )
                        //puede no tener una review
                );

        return jobContractCards;
    }


    @Override
    public void changeContractState(long id, JobContract.ContractState state) {
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
    }

    @Override
    public void changeContractScheduledDate(long id, String dateTime, boolean isServiceOwner, Locale locale) {
        String datePattern = messageSource.getMessage("spring.mvc.format.date-time", null, locale);
        LocalDateTime parsedDate = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(datePattern));

        jobContractDao.changeContractScheduledDate(id, parsedDate);

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
    public ByteImage findImageByContractId(long id) {
        return jobContractDao.findImageByContractId(id).orElseThrow(ImageNotFoundException::new);
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
}
