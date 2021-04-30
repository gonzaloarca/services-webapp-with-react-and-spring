package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private UserService userService;

    @Override
    public JobContract create(String clientEmail, long packageId, String description) {
        return create(clientEmail, packageId, description, null);
    }

    @Override
    public JobContract create(String clientEmail, long packageId, String description, ByteImage image) {
        User user = userService.findByEmail(clientEmail).orElseThrow(NoSuchElementException::new);

        jobPackageService.findById(packageId);
//        client = maybeUser.orElseGet(() -> userService.register(client_email,"", client_username, client_phone, 1));

        if(image == null)
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
    public List<JobContract> findByClientId(long id,int page) {
        return jobContractDao.findByClientId(id,page);
    }

    @Override
    public List<JobContract> findByProId(long id) {
        return jobContractDao.findByProId(id,HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobContract> findByProId(long id, int page) {
        return jobContractDao.findByProId(id,page);
    }

    @Override
    public List<JobContract> findByPostId(long id) {
        return jobContractDao.findByPostId(id,HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobContract> findByPostId(long id, int page) {
        return jobContractDao.findByPostId(id,page);
    }

    @Override
    public List<JobContract> findByPackageId(long id) {
        return jobContractDao.findByPackageId(id,HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobContract> findByPackageId(long id, int page) {
        return jobContractDao.findByPackageId(id,page);
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
    public int findMaxPageContractsByUserId(long id) {
        return jobContractDao.findMaxPageContractsByUserId(id);
    }

}
