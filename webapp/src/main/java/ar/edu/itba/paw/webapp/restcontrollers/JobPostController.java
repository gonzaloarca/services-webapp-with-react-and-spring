package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.dto.JobPostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Component
@Path("/jobpost")
public class JobPostController {
    private final Logger jobPostControllerLogger = LoggerFactory.getLogger(JobPostController.class);

    @Autowired
    PaginationService paginationService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JobContractService jobContractService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{postId}/")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response jobPostDetails(@PathParam("postId") final long id) {

        jobPostControllerLogger.debug("Finding job post by id: {}", id);
        JobPost jobPost = jobPostService.findByIdWithInactive(id);

        //TODO: SE DEBERIA HACER ACA? O ES UN REQUEST APARTE?
//        jobPostControllerLogger.debug("Finding images for post: {}", jobPost.getId());
//        List<Long> imageList = jobPostImageService.getImagesIdsByPostId(jobPost.getId());

        long totalJobPostContractsCompleted = jobContractService.findContractsQuantityByPostId(jobPost.getId());
        long totalReviewsSize = reviewService.findJobPostReviewsSize(id);
        Double avgRate = Math.floor(reviewService.findJobPostAvgRate(id) * 100) / 100;

        return Response.ok(
                JobPostDto.fromJobPostWithExtraData(jobPost, totalReviewsSize, avgRate, totalJobPostContractsCompleted)
        ).build();
    }

}
