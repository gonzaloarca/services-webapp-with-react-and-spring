package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import exceptions.JobPackageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Transactional
@Service
public class SimpleJobCardService implements JobCardService {

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobPostImageService jobPostImageService;

    @Autowired
    private ReviewService reviewService;


    @Override
    public List<JobCard> findAll() {
        //TODO: Mejorar excepcion
        return createCards(jobPostService.findAll());
    }

    @Override
    public List<JobCard> findAll(int page) {
        //TODO: Mejorar excepcion
        return createCards(jobPostService.findAll(page));
    }

    @Override
    public List<JobCard> findByUserId(long id) {
        //TODO: Mejorar excepcion
        return createCards(jobPostService.findByUserId(id));
    }

    @Override
    public List<JobCard> findByUserId(long id,int page) {
        //TODO: Mejorar excepcion
        return createCards(jobPostService.findByUserId(id,page));
    }

    @Override
    public List<JobCard> search(String title, JobPost.Zone zone, JobPost.JobType jobType) {
        //TODO: Mejorar excepcion
        return createCards(jobPostService.search(title, zone, jobType));
    }

    @Override
    public List<JobCard> search(String title, JobPost.Zone zone, JobPost.JobType jobType, int page) {
        //TODO: Mejorar excepcion
        return createCards(jobPostService.search(title, zone, jobType,page));
    }

    @Override
    public List<JobCard> findByUserIdWithReview(long id) {
        return createCards(jobPostService.findByUserId(id));
    }

    @Override
    public List<JobCard> findByUserIdWithReview(long id,int page) {
        return createCards(jobPostService.findByUserId(id,page));
    }

    @Override
    public JobCard findByPostId(long id) {
        JobPost jobPost = jobPostService.findById(id);
        JobPackage min = jobPackageService.findByPostId(jobPost.getId())
                .stream().min(Comparator.comparingDouble(JobPackage::getPrice)).orElseThrow(JobPackageNotFoundException::new);

        return new JobCard(jobPost, min.getRateType(), min.getPrice(),
                jobPostImageService.findPostImage(jobPost.getId()),
                jobContractService.findContractsQuantityByPostId(jobPost.getId()),
                reviewService.findJobPostReviewsSize(jobPost.getId()));
    }

    private List<JobCard> createCards(List<JobPost> jobPosts) {
        List<JobCard> jobCards = new ArrayList<>();
        jobPosts.forEach(jobPost -> {
            JobPackage min = jobPackageService.findByPostId(jobPost.getId())
                    .stream().min(Comparator.comparingDouble(JobPackage::getPrice)).orElseThrow(JobPackageNotFoundException::new);
            //TODO si no existe JobPackage para un PostId, no seria no mostrar la JobCard en vez de tirar excepcion que rompe todo?
            //TODO: Mejorar excepciones
            jobCards.add(new JobCard(jobPost, min.getRateType(), min.getPrice(),
                    jobPostImageService.findPostImage(jobPost.getId()),
                    jobContractService.findContractsQuantityByProId(jobPost.getUser().getId()),
                     reviewService.findJobPostReviewsSize(jobPost.getId())));
        });
        return jobCards;
    }

    @Override
    public int findSizeByUserId(long id) {
        return jobPostService.findSizeByUserId(id);
    }

}