package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.webapp.dto.output.JobCardDto;
import ar.edu.itba.paw.webapp.dto.output.JobCardOrderByDto;
import ar.edu.itba.paw.webapp.utils.CacheUtils;
import ar.edu.itba.paw.webapp.utils.LocaleResolverUtil;
import ar.edu.itba.paw.webapp.utils.PageResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.stream.Collectors;

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
    public Response search(
            @QueryParam("userId") final Long userId,
            @QueryParam("type") final String type,
            @QueryParam("zone") Integer zone,
            @Valid @Size(max = 100) @QueryParam("query") String query,
            @QueryParam("category") Integer jobType,
            @QueryParam("orderBy") Integer orderBy,
            @QueryParam("page") @DefaultValue("1") final int page
    ) {
        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        int maxPage;
        List<JobCard> jobCards;

        if (userId != null) {
            if (type != null && type.equalsIgnoreCase("related")) {
                JobCardControllerLogger.debug("Finding relatedJobCards for pro with id: {}", userId);
                maxPage = jobCardService.findRelatedJobCardsMaxPage(userId);
                jobCards = jobCardService.findRelatedJobCards(userId, page - 1);
            } else {
                JobCardControllerLogger.debug("Finding jobCards for pro with id: {}", userId);
                maxPage = jobCardService.findByUserIdMaxPage(userId);
                jobCards = jobCardService.findByUserId(userId, page - 1);
            }
        } else {
            JobCardControllerLogger.debug("Searching jobcards with data: zone: {}, query: {}, category: {}, orderby: {}, page: {}",
                    zone, query, jobType, orderBy, page);
            maxPage = jobCardService.searchMaxPage(query, zone, jobType, locale);
            jobCards = jobCardService.search(query, zone, jobType,
                    orderBy, page - 1, LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages()));
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
    @Path("/order-params")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response orderParams(@Context Request request) {
        List<JobCard.OrderBy> jobCardOrderBy = Arrays.asList(JobCard.OrderBy.values());
        List<JobCardOrderByDto> jobCardOrderByDtos = jobCardOrderBy.stream().map(orderBy -> JobCardOrderByDto.fromJobCardOrderByInfo(orderBy.getValue(),
                messageSource.getMessage(orderBy.getStringCode(), null, LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages()))
        )).collect(Collectors.toList());
        GenericEntity<List<JobCardOrderByDto>> entity = new GenericEntity<List<JobCardOrderByDto>>(jobCardOrderByDtos) {
        };
        return CacheUtils.sendConditionalCacheResponse(request, entity, new EntityTag(Integer.toString(jobCardOrderBy.hashCode())));
    }

    @GET
    @Path("/order-params/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response orderParams(@PathParam("id") int id, @Context Request request) {
        if (id < 0 || id > JobCard.OrderBy.values().length - 1)
            return Response.status(Response.Status.NOT_FOUND).build();
        JobCard.OrderBy orderBy = JobCard.OrderBy.values()[id];
        JobCardOrderByDto jobCardOrderByDto = JobCardOrderByDto.fromJobCardOrderByInfo(orderBy.getValue(),
                messageSource.getMessage(orderBy.getStringCode(), null, LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages())));
        GenericEntity<JobCardOrderByDto> entity = new GenericEntity<JobCardOrderByDto>(jobCardOrderByDto) {
        };
        return CacheUtils.sendConditionalCacheResponse(request, entity, new EntityTag(Integer.toString(orderBy.hashCode())));
    }
}
