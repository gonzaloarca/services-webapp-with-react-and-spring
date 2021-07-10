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
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("/review")
public class ReviewController {
    private final Logger reviewControllerLogger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    PaginationService paginationService;

    @Autowired
    private ReviewService reviewService;

    @Context
    private UriInfo uriInfo;

    @Path("/create")
    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createReview(final ReviewDto reviewDto) {
        long contractId = reviewDto.getJobContractDto().getId();
        reviewControllerLogger.debug("Finding contract with id {}", contractId);
        if (!reviewService.findContractReview(reviewDto.getJobContractDto().getId()).isPresent()) {
            reviewControllerLogger.debug("Creating review for contract {} with data: rate value: {}, title: {}, description: {}",
                    contractId, reviewDto.getRate(), reviewDto.getTitle(), reviewDto.getDescription());
            reviewService.create(contractId, reviewDto.getRate(), reviewDto.getTitle(), reviewDto.getDescription());
            final URI contractUri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(reviewDto.getJobContractDto().getId())).build();
            //FIXME: DEVUELVE MAL LA URI, CHEQUEAR LO QUE SE DIO EN CLASE
            return Response.created(contractUri).build();
        } else return Response.notModified().build();
    }

    @GET
    @Path("/contract_id/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response reviewByContractId(@PathParam("id") final long id) {
        reviewControllerLogger.debug("Finding review by contract id: {}", id);
        Review review = reviewService.findContractReview(id).orElseThrow(ReviewNotFoundException::new);
        return Response.ok(ReviewDto.fromReview(review)).build();
    }

    @GET
    @Path("/pro_avg/{id}")
    @Produces(value = {MediaType.TEXT_PLAIN})
    public Response reviewAvgByProId(@PathParam("id") final long id) {
        return Response.ok(Math.floor(reviewService.findProfessionalAvgRate(id) * 100) / 100).build();
    }

    @GET
    @Path("/post_id/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response reviewsByPostId(
            @PathParam("id") final long id,
            @QueryParam(value = "page") @DefaultValue("1") final int page) {
        if (page < 1) {
            reviewControllerLogger.debug("Invalid page: {}", page);
            throw new IllegalArgumentException();
        }

        reviewControllerLogger.debug("Finding reviews for post: {}", id);
        int maxPage = paginationService.findMaxPageReviewsByPostId(id);

        final List<ReviewDto> reviewDtoList = reviewService.findReviewsByPostId(id, page - 1)
                .stream().map(ReviewDto::fromReview).collect(Collectors.toList());
        //FIXME: SE VA A SEGUIR UTILIZANDO currentPages?
        // List<Integer> currentPages = paginationService.findCurrentPages(page, maxPage);

        return getReviewListResponse(page, maxPage, reviewDtoList);
    }

    @GET
    @Path("/pro_id/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response reviewsByProId(
            @PathParam("id") final long id,
            @QueryParam(value = "page") @DefaultValue("1") final int page) {
        if (page < 1) {
            reviewControllerLogger.debug("Invalid page: {}", page);
            throw new IllegalArgumentException();
        }

        reviewControllerLogger.debug("Finding reviews for pro with id: {}", id);
        int maxPage = paginationService.findMaxPageReviewsByUserId(id);

        final List<ReviewDto> reviewDtoList = reviewService.findReviewsByProId(id, page - 1)
                .stream().map(ReviewDto::fromReview).collect(Collectors.toList());
        //FIXME: SE VA A SEGUIR UTILIZANDO currentPages?
        // List<Integer> currentPages = paginationService.findCurrentPages(page, maxPage);

        return getReviewListResponse(page, maxPage, reviewDtoList);
    }

    private Response getReviewListResponse(@DefaultValue("1") @QueryParam("page") int page, int maxPage, List<ReviewDto> reviewDtoList) {
        return Response.ok(new GenericEntity<List<ReviewDto>>(reviewDtoList) {
        })
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build(), "first")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", maxPage).build(), "last")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", (page == 1) ? page : page - 1).build(), "prev")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", (page == maxPage) ? page : page + 1).build(), "next")
                .build();
    }
}
