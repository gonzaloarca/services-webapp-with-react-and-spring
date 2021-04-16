package ar.edu.itba.paw.services.nologin;

import ar.edu.itba.paw.interfaces.dao.JobPostDao;
import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoLoginJobPostService implements JobPostService {

    @Autowired
    private JobPostDao jobPostDao;

    @Autowired
    private UserService userService;

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobContractService jobContractService;

    @Override
    public JobPost create(String title, String availableHours, JobPost.JobType jobType, List<JobPost.Zone> zones) {


        //Chequeamos si el user esta registrado
//        Optional<User> maybeUser = userService.findByEmail(email);
        User user = new User();
        user.setId(-1);
//        Si existe vemos si es profesional, si no lo es lo hacemos
//        if (maybeUser.isPresent()) {
//            if (!maybeUser.get().isProfessional()) {
//                //TODO: Es necesario un optional?
//                Optional<User> newRole = userService.switchRole(maybeUser.get().getId());
//                user = newRole.orElse(maybeUser.get());
//            }else{
//                maybeUser = userService.updateUserByEmail(email,phone,username);
//                if(maybeUser.isPresent())
//                    user = maybeUser.get();
//            }
//        } else {
//            user=new User();
//            user.setId(-1);
//            user = userService.register(email,"", username, phone, true);
//        }
//        if (user == null)
//            //TODO: LANZAR EXCEPCION APROPIADA
//            throw new RuntimeException();
        return jobPostDao.create(user.getId(), title, availableHours, jobType, zones);
    }

    @Override
    public Optional<JobPost> findById(long id) {
        return jobPostDao.findById(id);
    }

    @Override
    public Optional<List<JobPost>> findByUserId(long id) {
        return jobPostDao.findByUserId(id);
    }

    @Override
    public Optional<List<JobPost>> findByJobType(JobPost.JobType jobType) {
        return jobPostDao.findByJobType(jobType);
    }

    @Override
    public Optional<List<JobPost>> findByZone(JobPost.Zone zone) {
        return jobPostDao.findByZone(zone);
    }

    @Override
    public Optional<List<JobPost>> findAll() {
        return jobPostDao.findAll();
    }

    @Override
    public Optional<JobPackage> findCheapestPackage(long postId) {
        List<JobPackage> jobPackages = jobPackageService.findByPostId(postId).orElseThrow(
                RuntimeException::new //TODO: DEBERIA SER UNA EXCEPCION PROPIA
        );

        return jobPackages.stream().min(Comparator.comparingDouble(JobPackage::getPrice));
    }

    @Override
    public Optional<List<JobPost>> search(String title, JobPost.Zone zone) {
        return jobPostDao.search(title, zone);
    }
}
