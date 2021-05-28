package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.interfaces.services.JobCardService;
import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobContractCard;
import ar.edu.itba.paw.models.User;
import exceptions.JobPackageNotFoundException;
import exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public JobContract create(String clientEmail, long packageId, String description) {
        return create(clientEmail, packageId, description, null);
    }

    @Override
    public JobContract create(String clientEmail, long packageId, String description, ByteImage image) {
        User user = userService.findByEmail(clientEmail).orElseThrow(UserNotFoundException::new);

        if (image == null)
            return jobContractDao.create(user.getId(), packageId, description);

        return jobContractDao.create(user.getId(), packageId, description, image);
    }

    @Override
    public JobContract findById(long id) {
        return jobContractDao.findById(id).orElseThrow(JobPackageNotFoundException::new);
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
    public int findMaxPageContractsByClientIdAndStates(long id, List<JobContract.ContractState> states) {
        return jobContractDao.findMaxPageContractsByClientIdAndStates(id, states);
    }

    @Override
    public int findMaxPageContractsByProIdAndStates(long id, List<JobContract.ContractState> states) {
        return jobContractDao.findMaxPageContractsByProIdAndStates(id, states);
    }

    @Override
    public List<JobContractCard> findJobContractCardsByProIdAndStates(long id, List<JobContract.ContractState> states, int page) {
        List<JobContractCard> jobContractCards = new ArrayList<>();

        List<JobContract> jobContracts = findByProId(id, page);

        for (JobContract contract : jobContracts) {
            if (states.contains(contract.getState()))
                jobContractCards.add(
                        new JobContractCard(contract, jobCardService
                                .findByPostIdWithInactive(contract.getJobPackage().getPostId()),
                                reviewService.findContractReview(contract.getId()).orElse(null)));
            //puede no tener una review
        }

        return jobContractCards;
    }

    @Override
    public List<JobContractCard> findJobContractCardsByClientIdAndStates(long id, List<JobContract.ContractState> states, int page) {
        List<JobContractCard> jobContractCards = new ArrayList<>();

        List<JobContract> jobContracts = findByClientId(id, page);

        for (JobContract contract : jobContracts) {
            if (states.contains(contract.getState()))
                jobContractCards.add(
                        new JobContractCard(contract, jobCardService
                                .findByPostIdWithInactive(contract.getJobPackage().getPostId()),
                                reviewService.findContractReview(contract.getId()).orElse(null)));
            //puede no tener una review
        }

        return jobContractCards;
    }



    @Override
    public void changeContractState(long id, JobContract.ContractState state) {
        jobContractDao.changeContractState(id, state);
    }
}
