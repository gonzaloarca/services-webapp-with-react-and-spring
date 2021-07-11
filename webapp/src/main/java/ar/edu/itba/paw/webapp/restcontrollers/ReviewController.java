package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.webapp.dto.ReviewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Component
@Path("/reviews")
public class ReviewController {
    private final Logger reviewControllerLogger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    PaginationService paginationService;

    @Autowired
    private ReviewService reviewService;

    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response create(final ReviewDto reviewDto) {
        long contractId = reviewDto.getJobContract().getId();
        reviewControllerLogger.debug("Finding contract with id {}", contractId);
        if (!reviewService.findContractReview(reviewDto.getJobContract().getId()).isPresent()) {
            reviewControllerLogger.debug("Creating review for contract {} with data: rate value: {}, title: {}, description: {}",
                    contractId, reviewDto.getRate(), reviewDto.getTitle(), reviewDto.getDescription());
            reviewService.create(contractId, reviewDto.getRate(), reviewDto.getTitle(), reviewDto.getDescription());
            final URI contractUri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(reviewDto.getJobContract().getId())).build();
            return Response.created(contractUri).build();
        } else return Response.notModified().build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response reviewByContractId(@PathParam("id") final long id) {
        reviewControllerLogger.debug("Finding review by contract id: {}", id);
        Review review = reviewService.findContractReview(id).orElseThrow(ReviewNotFoundException::new);
        return Response.ok(ReviewDto.fromReview(review, uriInfo)).build();
    }

}
