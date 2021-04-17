package ar.edu.itba.paw.services.nologin;

import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NoLoginJobContractService implements JobContractService {

    @Autowired
    private JobContractDao jobContractDao;

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private UserService userService;

    @Override
    public JobContract create(String clientEmail, long packageId, String description) {
        return create(clientEmail,packageId, description, null);
    }

    @Override
    public JobContract create(String clientEmail,long packageId, String description, byte[] imageData) {
        User user = userService.findByEmail(clientEmail).orElseThrow(NoSuchElementException::new);

        jobPackageService.findById(packageId);
//        client = maybeUser.orElseGet(() -> userService.register(client_email,"", client_username, client_phone, 1));

        return jobContractDao.create(user.getId(), packageId, description, imageData);
    }

    @Override
    public JobContract findById(long id) {
        return jobContractDao.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<JobContract> findByClientId(long id) {
        return jobContractDao.findByClientId(id);
    }

    @Override
    public List<JobContract> findByProId(long id) {
        return jobContractDao.findByProId(id);
    }

    @Override
    public List<JobContract> findByPostId(long id) {
        return jobContractDao.findByPostId(id);
    }

    @Override
    public List<JobContract> findByPackageId(long id) {
        return jobContractDao.findByPackageId(id);
    }

    @Override
    public int findContractsQuantityByProId(long id) {
        return jobContractDao.findContractsQuantityByProId(id);
    }

}
