package ar.edu.itba.paw.services.nologin;

import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoLoginJobContractService implements JobContractService {

    @Autowired
    private JobContractDao jobContract;

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private UserService userService;

    @Override
    public JobContract create(long packageId, String description, String client_email, String client_username, String client_phone) {
        Optional<User> maybeUser = userService.findByEmail(client_email);
        User client;
        client = maybeUser.orElseGet(() -> userService.register(client_email, client_username, client_phone, false));

        if(!jobPackageService.findById(packageId).isPresent())
            // TODO: CAMBIAR A EXCEPCION PROPIA
            throw new RuntimeException("Package no encontrado");

        return jobContract.create(client.getId(), packageId, description);
    }

    @Override
    public Optional<JobContract> findById(long id) {
        return jobContract.findById(id);
    }

    @Override
    public Optional<List<JobContract>> findByClientId(long id) {
        return jobContract.findByClientId(id);
    }

    @Override
    public Optional<List<JobContract>> findByProId(long id) {
        return jobContract.findByProId(id);
    }

    @Override
    public Optional<List<JobContract>> findByPostId(long id) {
        return jobContract.findByPostId(id);
    }

    @Override
    public Optional<List<JobContract>> findByPackageId(long id) {
        return jobContract.findByPackageId(id);
    }

}
