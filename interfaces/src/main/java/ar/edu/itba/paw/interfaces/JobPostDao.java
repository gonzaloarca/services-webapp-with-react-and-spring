package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobType;
import ar.edu.itba.paw.models.Zone;

import java.util.List;
import java.util.Optional;

public interface JobPostDao {

    JobPost create(long userId, String title, String availableHours, JobType jobType);

    Optional<JobPost> findById(long id);

    Optional<List<JobPost>> findByUserId(long id);

    Optional<List<JobPost>> findByJobType(JobType jobType);

    Optional<List<JobPost>> findByZone(Zone zone);

    Optional<List<JobPost>> findAll();
}
