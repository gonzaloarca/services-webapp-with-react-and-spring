package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.dto.ProfessionalDto;
import ar.edu.itba.paw.webapp.dto.ReviewDto;
import ar.edu.itba.paw.webapp.utils.PageResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
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
    private ReviewService reviewService;

    @Autowired
    private JobContractService jobContractService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") final long id) {
        professionalControllerLogger.debug("Finding user with email {}", id);
        User user = userService.findById(id);
        professionalControllerLogger.debug("Finding auth info for user with email {}", user.getEmail());
        UserAuth auth = userService.getAuthInfo(user.getEmail()).orElseThrow(UserNotFoundException::new);

        if (!auth.getRoles().contains(UserAuth.Role.PROFESSIONAL))
            return Response.noContent().build();//FIXME: Que hacer si no es PRO?

        ProfessionalDto proAnswer = ProfessionalDto.fromUserAndRoles(user, auth.getRoles(),
                reviewService.findProfessionalAvgRate(id),
                reviewService.findReviewsSizeByProId(id),
                jobContractService.findCompletedContractsQuantityByProId(id));
        return Response.ok(proAnswer).build();
    }

    @GET
    @Path("/{id}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response reviews(
            @PathParam("id") final long id,
            @QueryParam(value = "page") @DefaultValue("1") final int page) {
        if (page < 1) {
            professionalControllerLogger.debug("Invalid page: {}", page);
            throw new IllegalArgumentException();
        }

        professionalControllerLogger.debug("Finding reviews for pro with id: {}", id);
        int maxPage = paginationService.findMaxPageReviewsByUserId(id);

        final List<ReviewDto> reviewDtoList = reviewService.findReviewsByProId(id, page - 1)
                .stream().map(review -> ReviewDto.fromReview(review, uriInfo)).collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page - 1, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<ReviewDto>>(reviewDtoList) {
                }));
    }
}
