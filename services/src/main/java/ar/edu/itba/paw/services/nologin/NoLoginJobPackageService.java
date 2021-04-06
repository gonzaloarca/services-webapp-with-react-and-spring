package ar.edu.itba.paw.services.nologin;

import ar.edu.itba.paw.interfaces.dao.JobPackageDao;
import ar.edu.itba.paw.interfaces.services.JobPackageService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
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
        //TODO: CAMBIAR POR EXCEPCIONES PROPIAS
        if (!jobPostService.findById(postId).isPresent())
            throw new RuntimeException("Post no encontrado");

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

    @Override
    public Map<JobPackage, String> findByPostIdWithPrice(long id) {
        Map<JobPackage, String> answer = new HashMap<>();
        Optional<List<JobPackage>> jobPackages = findByPostId(id);
        if (!jobPackages.isPresent())
            return answer;

        jobPackages.get().forEach(jobPackage ->
                answer.put(jobPackage, getPriceFormat(jobPackage.getPrice(), jobPackage.getRateType())
                )
        );

        return answer;
    }

    public String getPriceFormat(Double price, JobPackage.RateType rateType) {
        String ans;
        if (rateType == JobPackage.RateType.HOURLY)
            ans = "$" + price + "/hora";
        else if (rateType == JobPackage.RateType.ONE_TIME)
            ans = "$" + price;
        else ans = "A acordar";

        return ans;
    }
}
