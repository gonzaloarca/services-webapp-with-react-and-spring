package ar.edu.itba.paw.services.nologin;

import ar.edu.itba.paw.interfaces.dao.JobPackageDao;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.JobPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoLoginJobPackageService implements JobPackageService {

    @Autowired
    private JobPackageDao jobPackageDao;

    @Autowired
    private JobPostService jobPostService;

    @Override
    public JobPackage create(long postId, String title, String description, double price, JobPackage.RateType rateType) {
        jobPostService.findById(postId);        //Para verificar que existe el Post
        return jobPackageDao.create(postId, title, description, price, rateType);
    }

    @Override
    public Optional<JobPackage> findById(long id) {
        return jobPackageDao.findById(id);
    }

    @Override
    public Optional<List<JobPackage>> findByPostId(long id) {
        return jobPackageDao.findByPostId(id);
    }

}
