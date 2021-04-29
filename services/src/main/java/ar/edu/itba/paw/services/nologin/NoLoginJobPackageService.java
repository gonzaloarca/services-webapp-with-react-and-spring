package ar.edu.itba.paw.services.nologin;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobPackageDao;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.JobPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class NoLoginJobPackageService implements JobPackageService {

    @Autowired
    private JobPackageDao jobPackageDao;

    @Autowired
    private JobPostService jobPostService;

    @Override
    public JobPackage create(long postId, String title, String description, String price, int rateType) {
        //TODO: CAMBIAR POR EXCEPCIONES PROPIAS

        jobPostService.findById(postId);

        //FIXME: DO BEFORE
        Double parsedPrice=null;

        JobPackage.RateType parsedRateType = JobPackage.RateType.values()[rateType];

        //TODO: CAMBIAR POR EXCEPCIONES PROPIAS
        if(!parsedRateType.equals(JobPackage.RateType.TBD)) {
            if (price != null && !price.isEmpty()) {
                parsedPrice = Double.parseDouble(price);
            }else {
                throw new RuntimeException("Error al cargar el form");
            }
        }


        return jobPackageDao.create(postId, title, description, parsedPrice, parsedRateType);
    }

    @Override
    public JobPackage findById(long id) {
        return jobPackageDao.findById(id).orElseThrow(NoSuchElementException::new);
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

        //FIXME: DO BEFORE
        Double parsedPrice=null;
        JobPackage.RateType parsedRateType = JobPackage.RateType.values()[rateType];
        if(!parsedRateType.equals(JobPackage.RateType.TBD)) {
            if (price != null && !price.isEmpty()) {
                parsedPrice = Double.parseDouble(price);
            }else {
                throw new RuntimeException("Error al cargar el form");
            }
        }

        return jobPackageDao.updatePackage(id,title,description,parsedPrice,parsedRateType);
    }

    @Override
    public boolean deleteJobPackage(long id){
        return jobPackageDao.deletePackage(id);
    }


}
