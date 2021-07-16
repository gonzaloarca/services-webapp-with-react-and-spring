package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.webapp.dto.JobCardDto;
import ar.edu.itba.paw.webapp.utils.LocaleResolverUtil;
import ar.edu.itba.paw.webapp.utils.PageResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static ar.edu.itba.paw.interfaces.HirenetUtils.SEARCH_WITHOUT_CATEGORIES;

@Component
@Path("/job-cards")
public class JobCardController {
    private final Logger JobCardControllerLogger = LoggerFactory.getLogger(JobCardController.class);

    @Autowired
    PaginationService paginationService;

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
            @QueryParam(value = "page") @DefaultValue("1") int page) {
        if (page < 1) {
            page = 1;
        }
        JobCardControllerLogger.debug("Finding all jobcards for page: {}", page);
        int maxPage = paginationService.findJobCardsMaxPage();
        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        final List<JobCardDto> jobCardDtoList = jobCardService.findAll(page - 1)
                .stream().map(jobCard -> JobCardDto.fromJobCardWithLocalizedMessage(jobCard, uriInfo,
                        messageSource.getMessage(jobCard.getJobPost().getJobType().getDescription(), null, locale)))
                .collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<JobCardDto>>(jobCardDtoList) {
                }));
    }

    @GET
    @Path("/search")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response search(@QueryParam(value = "zone") @NotNull final int zone,
                           @QueryParam(value = "query") String query,
                           @QueryParam(value = "category") Integer category,
                           @QueryParam(value = "orderBy") Integer orderBy,
                           @QueryParam(value = "page") @DefaultValue("1") int page) {
        if (category == null)
            category = SEARCH_WITHOUT_CATEGORIES;
        if (orderBy == null)
            orderBy = JobCard.OrderBy.BETTER_QUALIFIED.ordinal();
        if (page < 1) {
            page = 1;
        }

        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());

        JobCardControllerLogger.debug("searching jobcards with data: zone: {}, query: {}, category: {}, orderby: {}, page: {}", zone, query, category, orderBy, page);
        int maxPage = paginationService
                .findJobCardsSearchMaxPage(query, zone, category, locale);

        final List<JobCardDto> jobCardDtoList = jobCardService.search(query, zone, category,
                orderBy, page - 1, LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages()))
                .stream().map(jobCard -> JobCardDto.fromJobCardWithLocalizedMessage(jobCard, uriInfo,
                        messageSource.getMessage(jobCard.getJobPost().getJobType().getDescription(), null, locale)))
                .collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<JobCardDto>>(jobCardDtoList) {
                }));
    }
}
