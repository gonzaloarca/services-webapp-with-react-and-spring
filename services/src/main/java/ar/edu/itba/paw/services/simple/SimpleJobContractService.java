package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobContractCard;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class SimpleJobContractService implements JobContractService {

    @Autowired
    private JobContractDao jobContractDao;

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private JobCardService jobCardService;

    @Autowired
    private ReviewService reviewService;

    @Override
    public JobContract create(String clientEmail, long packageId, String description) {
        return create(clientEmail, packageId, description, null);
    }

    @Override
    public JobContract create(String clientEmail, long packageId, String description, ByteImage image) {
        User user = userService.findByEmail(clientEmail).orElseThrow(NoSuchElementException::new);

        if (image == null)
            return jobContractDao.create(user.getId(), packageId, description);

        return jobContractDao.create(user.getId(), packageId, description, image);
    }

    @Override
    public JobContract findById(long id) {
        return jobContractDao.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<JobContract> findByClientId(long id) {
        return jobContractDao.findByClientId(id, HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobContract> findByClientId(long id, int page) {
        return jobContractDao.findByClientId(id, page);
    }

    @Override
    public List<JobContract> findByProId(long id) {
        return jobContractDao.findByProId(id, HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobContract> findByProId(long id, int page) {
        return jobContractDao.findByProId(id, page);
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
    public int findContractsQuantityByProId(long id) {
        return jobContractDao.findContractsQuantityByProId(id);
    }

    @Override
    public int findContractsQuantityByPostId(long id) {
        return jobContractDao.findContractsQuantityByPostId(id);
    }

    @Override
    public int findMaxPageContractsByClientId(long id) {
        return jobContractDao.findMaxPageContractsByClientId(id);
    }

    @Override
    public int findMaxPageContractsByProId(long id) {
        return jobContractDao.findMaxPageContractsByProId(id);
    }

    @Override
    public List<JobContractCard> findJobContractCardsByClientId(long id, int page) {
        List<JobContractCard> jobContractCards = new ArrayList<>();

        findByClientId(id, page).
                forEach(jobContract ->
                                jobContractCards.add(
                                        new JobContractCard(jobContract, jobCardService.findByPostIdWithInactive(jobContract.getJobPackage().getPostId()),
                                                reviewService.findContractReview(jobContract.getId()).orElse(null)))
                        //puede no tener una review
                );
        return jobContractCards;
    }

    @Override
    public List<JobContractCard> findJobContractCardsByProId(long id, int page) {
        List<JobContractCard> jobContractCards = new ArrayList<>();

        findByProId(id, page)
                .forEach(jobContract ->
                                jobContractCards.add(
                                        new JobContractCard(jobContract, jobCardService
                                                .findByPostIdWithInactive(jobContract.getJobPackage().getPostId()),
                                                reviewService.findContractReview(jobContract.getId()).orElse(null)))
                        //puede no tener una review
                );
        return jobContractCards;
    }
}
