package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.webapp.dto.output.JobCardDto;
import ar.edu.itba.paw.webapp.dto.output.JobCardOrderByDto;
import ar.edu.itba.paw.webapp.utils.LocaleResolverUtil;
import ar.edu.itba.paw.webapp.utils.PageResponseUtil;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static ar.edu.itba.paw.interfaces.HirenetUtils.SEARCH_WITHOUT_CATEGORIES;
import static ar.edu.itba.paw.interfaces.HirenetUtils.SEARCH_WITHOUT_ZONES;

@Component
@Path("/job-cards")
public class JobCardController {
    private final Logger JobCardControllerLogger = LoggerFactory.getLogger(JobCardController.class);

    @Autowired
    private JobCardService jobCardService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private MessageSource messageSource;

    @Context
    private HttpHeaders headers;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findAll(
            @QueryParam("userId") final Long userId,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("type") final String type) {
        if (page < 1)
            page = 1;

        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        int maxPage;
        List<JobCard> jobCards;

        if (userId == null) {
            JobCardControllerLogger.debug("Finding all jobcards for page: {}", page);
            maxPage = jobCardService.findAllMaxPage();
            jobCards = jobCardService.findAll(page - 1);
        } else if (type != null && type.equalsIgnoreCase("related")) {
            JobCardControllerLogger.debug("Finding relatedJobCards for pro with id: {}", userId);
            maxPage = jobCardService.findRelatedJobCardsMaxPage(userId);
            jobCards = jobCardService.findRelatedJobCards(userId, page - 1);
        } else {
            JobCardControllerLogger.debug("Finding jobCards for pro with id: {}", userId);
            maxPage = jobCardService.findByUserIdMaxPage(userId);
            jobCards = jobCardService.findByUserId(userId, page - 1);
        }

        List<JobCardDto> jobCardDtoList = jobCards.stream()
                .map(jobCard -> JobCardDto.fromJobCardWithLocalizedMessage(jobCard, uriInfo,
                        messageSource.getMessage(jobCard.getJobPost().getJobType().getDescription(), null, locale)))
                .collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<JobCardDto>>(jobCardDtoList) {
                }));
    }


    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("id") long id) {
        JobCardControllerLogger.debug("Finding card for post with id: {}", id);
        JobCard jobCard = jobCardService.findByPostId(id);
        JobCardDto jobCardDto = JobCardDto.fromJobCardWithLocalizedMessage(jobCard, uriInfo,
                messageSource.getMessage(jobCard.getJobPost().getJobType().getDescription(), null, LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages())));
        return Response.ok(new GenericEntity<JobCardDto>(jobCardDto) {
        }).build();
    }

    @GET
    @Path("/search")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response search(@QueryParam("zone") Integer zone,
                           @Valid @Size(max = 100) @QueryParam("query") String query,
                           @QueryParam("category") Integer category,
                           @QueryParam("orderBy") Integer orderBy,
                           @QueryParam("page") @DefaultValue("1") int page) {

        if (category == null)
            category = SEARCH_WITHOUT_CATEGORIES;
        if (orderBy == null)
            orderBy = JobCard.OrderBy.BETTER_QUALIFIED.ordinal();
        if (zone == null)
            zone = SEARCH_WITHOUT_ZONES;
        if (page < 1)
            page = 1;

        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());

        JobCardControllerLogger.debug("searching jobcards with data: zone: {}, query: {}, category: {}, orderby: {}, page: {}", zone, query, category, orderBy, page);
        int maxPage = jobCardService.searchMaxPage(query, zone, category, locale);

        final List<JobCardDto> jobCardDtoList = jobCardService.search(query, zone, category,
                orderBy, page - 1, LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages()))
                .stream().map(jobCard -> JobCardDto.fromJobCardWithLocalizedMessage(jobCard, uriInfo,
                        messageSource.getMessage(jobCard.getJobPost().getJobType().getDescription(), null, locale)))
                .collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<JobCardDto>>(jobCardDtoList) {
                }));
    }


    @GET
    @Path("/order-params")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response orderParams() {
        List<JobCardOrderByDto> jobCardOrderByDtos = Arrays.stream(JobCard.OrderBy.values()).map(orderBy -> JobCardOrderByDto.fromJobCardOrderByInfo(orderBy.getValue(),
                messageSource.getMessage(orderBy.getStringCode(), null, LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages()))
        )).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<JobCardOrderByDto>>(jobCardOrderByDtos) {
        }).build();
    }

    @GET
    @Path("/order-params/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response orderParams(@PathParam("id") int id) {
        if (id < 0 || id > JobCard.OrderBy.values().length - 1)
            return Response.status(Response.Status.NOT_FOUND).build();
        JobCard.OrderBy orderBy = JobCard.OrderBy.values()[id];
        JobCardOrderByDto jobCardOrderByDto = JobCardOrderByDto.fromJobCardOrderByInfo(orderBy.getValue(),
                messageSource.getMessage(orderBy.getStringCode(), null, LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages())));
        return Response.ok(new GenericEntity<JobCardOrderByDto>(jobCardOrderByDto) {
        }).build();
    }
}
