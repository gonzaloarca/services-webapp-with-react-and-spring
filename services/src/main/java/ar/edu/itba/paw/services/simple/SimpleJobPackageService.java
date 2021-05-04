package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobPackageDao;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import exceptions.JobPackageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class SimpleJobPackageService implements JobPackageService {

    @Autowired
    private JobPackageDao jobPackageDao;

    @Override
    public JobPackage create(long postId, String title, String description, String price, int rateType) {

        JobPackage.RateType parsedRateType = JobPackage.RateType.values()[rateType];
        Double parsedPrice = parsePrice(parsedRateType,price);

        return jobPackageDao.create(postId, title, description, parsedPrice, parsedRateType);
    }

    @Override
    public JobPackage findById(long id) {
        return jobPackageDao.findById(id).orElseThrow(JobPackageNotFoundException::new);
    }

    @Override
    public List<JobPackage> findByPostId(long id) {
        return jobPackageDao.findByPostId(id, HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobPackage> findByPostId(long id,int page) {
        return jobPackageDao.findByPostId(id,page);
    }

    @Override
    public boolean updateJobPackage(long id, String title, String description, String price, int rateType){
        JobPackage.RateType parsedRateType = JobPackage.RateType.values()[rateType];
        Double parsedPrice = parsePrice(parsedRateType,price);

        return jobPackageDao.updatePackage(id,title,description,parsedPrice,parsedRateType);
    }

    @Override
    public boolean deleteJobPackage(long id){
        return jobPackageDao.deletePackage(id);
    }

    private Double parsePrice(JobPackage.RateType rateType, String price){
        if(!rateType.equals(JobPackage.RateType.TBD)) {
            if (price != null && !price.isEmpty()) {
                return Double.parseDouble(price);
            }else {
                throw new RuntimeException("Error loading form");
            }
        }
        return null;
    }


}
