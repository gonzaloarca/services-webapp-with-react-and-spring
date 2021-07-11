package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.webapp.dto.JobCardDto;
import ar.edu.itba.paw.webapp.utils.PageResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
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

    @Context
    HttpHeaders headers;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findAll(
            @QueryParam(value = "page") @DefaultValue("1") final int page) {
        if (page < 1) {
            JobCardControllerLogger.debug("Invalid page: {}", page);
            throw new IllegalArgumentException();
        }

        int maxPage = paginationService.findJobCardsMaxPage();
        final List<JobCardDto> jobCardDtoList = jobCardService.findAll(page - 1)
                .stream().map(jobCard -> JobCardDto.fromJobCard(jobCard, uriInfo)).collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page - 1, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<JobCardDto>>(jobCardDtoList) {
                }));
    }

    @GET
    @Path("/search")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response search(@QueryParam(value = "zone") @NotNull final int zone,
                           @QueryParam(value = "query") final String query,
                           @QueryParam(value = "category") Integer category,
                           @QueryParam(value = "orderBy") Integer orderBy,
                           @QueryParam(value = "page") @DefaultValue("1") final int page) {
        if (category == null)
            category = SEARCH_WITHOUT_CATEGORIES;
        if (orderBy == null)
            orderBy = JobCard.OrderBy.BETTER_QUALIFIED.ordinal();
        if (page < 1) {
            JobCardControllerLogger.debug("Invalid page: {}", page);
            throw new IllegalArgumentException();
        }

        int maxPage = paginationService
                .findJobCardsSearchMaxPage(query, zone, category, headers.getAcceptableLanguages().get(0));
        final List<JobCardDto> jobCardDtoList = jobCardService.search(query, zone, category,
                orderBy, page - 1, headers.getAcceptableLanguages().get(0))
                .stream().map(jobCard -> JobCardDto.fromJobCard(jobCard, uriInfo)).collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page - 1, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<JobCardDto>>(jobCardDtoList) {
                }));
    }
}
