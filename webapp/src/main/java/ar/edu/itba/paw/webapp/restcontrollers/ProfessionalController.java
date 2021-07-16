package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import ar.edu.itba.paw.models.exceptions.ProfessionalNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.dto.*;
import ar.edu.itba.paw.webapp.utils.LocaleResolverUtil;
import ar.edu.itba.paw.webapp.utils.PageResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@Path("/professionals")
public class ProfessionalController {
    private final Logger professionalControllerLogger = LoggerFactory.getLogger(ProfessionalController.class);

    @Autowired
    PaginationService paginationService;

    @Autowired
    private UserService userService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobCardService jobCardService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private MessageSource messageSource;

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders headers;

    @GET
    @Path("/{id}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response reviews(
            @PathParam("id") final long id,
            @QueryParam(value = "page") @DefaultValue("1") int page) {
        if (page < 1) {
            page = 1;
        }

        professionalControllerLogger.debug("Finding reviews for pro with id: {}", id);
        int maxPage = paginationService.findReviewsByUserIdMaxPage(id);
        final List<ReviewDto> reviewDtoList = reviewService.findReviewsByProId(id, page - 1)
                .stream().map(review -> ReviewDto.fromReview(review, uriInfo)).collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<ReviewDto>>(reviewDtoList) {
                }));
    }

    @GET
    @Path("/{id}/reviews-by-exact-rate")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response reviews(@PathParam("id") final long id) {
        professionalControllerLogger.debug("Finding reviews by exact rates for pro with id: {}", id);
        return Response.ok(ReviewsByExactRateDto.fromListWithRates(reviewService.findProfessionalReviewsByPoints(id)))
                .build();
    }

    @GET
    @Path("/{id}/job-cards")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response jobCards(
            @PathParam("id") final long id,
            @QueryParam(value = "page") @DefaultValue("1") int page) {
        if (page < 1) {
            page = 1;
        }

        professionalControllerLogger.debug("Finding jobCards for pro with id: {}", id);
        int maxPage = paginationService.findJobCardsByUserIdMaxPage(id);
        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        final List<JobCardDto> jobCardDtoList = jobCardService.findByUserId(id, page - 1)
                .stream().map(jobCard -> JobCardDto.fromJobCardWithLocalizedMessage(jobCard, uriInfo,
                        messageSource.getMessage(jobCard.getJobPost().getJobType().getDescription(), null, locale)))
                .collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<JobCardDto>>(jobCardDtoList) {
                }));
    }

    @Path("/{id}/category-rankings")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getCategoryRankings(@PathParam("id") long id) {
        professionalControllerLogger.debug("Finding analytics rankings for user {}", id);
        List<AnalyticRankingDto> rankingDtoList = userService.findUserAnalyticRankings(id).stream()
                .map(ranking -> AnalyticRankingDto.fromAnalyticRanking(ranking,
                        messageSource.getMessage(ranking.getJobType().getDescription(), null,
                                LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages()))))
                .collect(Collectors.toList());

        return Response.ok(new GenericEntity<List<AnalyticRankingDto>>(rankingDtoList) {
        }).build();
    }

    @Path("/{id}/related-job-cards")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getRelatedJobCards(@PathParam("id") long id,
                                       @QueryParam(value = "page") @DefaultValue("1") int page) {
        if (page < 1) {
            page = 1;
        }

        professionalControllerLogger.debug("Finding relatedJobCards for pro with id: {}", id);
        int maxPage = paginationService.findRelatedJobCardsMaxPage(id);
        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        final List<JobCardDto> jobCardDtoList = jobCardService.findRelatedJobCards(id, page - 1)
                .stream().map(jobCard -> JobCardDto.fromJobCardWithLocalizedMessage(jobCard, uriInfo,
                        messageSource.getMessage(jobCard.getJobPost().getJobType().getDescription(), null, locale)))
                .collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<JobCardDto>>(jobCardDtoList) {
                }));
    }
}
