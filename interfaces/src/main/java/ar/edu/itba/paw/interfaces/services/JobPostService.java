package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface JobPostService {

    JobPost create(String email, String username, String phone, String title, String availableHours, JobPost.JobType jobType, List<JobPost.Zone> zones);

    Optional<JobPost> findById(long id);

    Optional<List<JobPost>> findByUserId(long id);

    Optional<List<JobPost>> findByJobType(JobPost.JobType jobType);

    Optional<List<JobPost>> findByZone(JobPost.Zone zone);

    Optional<List<JobPost>> findAll();

    Map<JobPost, List<String>> findAllJobCardsWithData();
    //En List<String> el elemento 0 indica el precio del package mas barato del post
    // y el 1 la cantidad de trabajos completados por el profesional

    Optional<List<JobPost>> search(String title, JobPost.Zone zone);

}
