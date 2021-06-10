package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.models.exceptions.JobContractNotFoundException;
import ar.edu.itba.paw.models.exceptions.JobPackageNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import com.sun.xml.internal.ws.encoding.xml.XMLMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
    public List<JobContractCard> findJobContractCardsByProId(long id, List<JobContract.ContractState> states, int page) {
        List<JobContractCard> jobContractCards = new ArrayList<>();

        findByProId(id, states, page)
                .forEach(jobContract ->
                                jobContractCards.add(
                                        new JobContractCard(jobContract, jobCardService
                                                .findByPostIdWithInactive(jobContract.getJobPackage().getPostId()),
                                                reviewService.findContractReview(jobContract.getId()).orElse(null)))
                        //puede no tener una review
                );

        return jobContractCards;
    }

    @Override
    public List<JobContractCard> findJobContractCardsByClientId(long id, List<JobContract.ContractState> states, int page) {
        List<JobContractCard> jobContractCards = new ArrayList<>();

        findByClientId(id, states, page).
                forEach(jobContract ->
                                jobContractCards.add(
                                        new JobContractCard(jobContract,
                                                jobCardService.findByPackageIdWithPackageInfoWithInactive(jobContract.getJobPackage().getId()),
                                                reviewService.findContractReview(jobContract.getId()).orElse(null)))
                        //puede no tener una review
                );

        return jobContractCards;
    }


    @Override
    public void changeContractState(long id, JobContract.ContractState state) {
        jobContractDao.changeContractState(id, state);
    }

    @Override
    public JobContractWithImage findJobContractWithImage(long id) {
        return jobContractDao.findJobContractWithImage(id).orElseThrow(JobContractNotFoundException::new);
    }

    @Override
    public ByteImage findImageByContractId(long id) {
        return jobContractDao.findImageByContractId(id).orElseThrow(ImageNotFoundException::new);
    }
}
