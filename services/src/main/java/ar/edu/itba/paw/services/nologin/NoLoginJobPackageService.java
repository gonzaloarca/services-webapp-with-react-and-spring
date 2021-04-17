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
    public JobPackage create(long postId, String title, String description, String price, int rateType) {
        //TODO: CAMBIAR POR EXCEPCIONES PROPIAS
        if (!jobPostService.findById(postId).isPresent())
            throw new RuntimeException("Post no encontrado");

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
    public Optional<JobPackage> findById(long id) {
        return jobPackageDao.findById(id);
    }

    @Override
    public List<JobPackage> findByPostId(long id) {
        return jobPackageDao.findByPostId(id);
    }

}
