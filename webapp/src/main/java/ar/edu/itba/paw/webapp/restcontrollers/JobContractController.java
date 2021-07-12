package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobContractWithImage;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.dto.JobContractDto;
import ar.edu.itba.paw.webapp.dto.JobPostDto;
import ar.edu.itba.paw.webapp.dto.ReviewDto;
import ar.edu.itba.paw.webapp.utils.LocaleResolverUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Locale;

@Component
@Path("/job-contracts")
public class JobContractController {
    private final Logger jobContractControllerLogger = LoggerFactory.getLogger(JobContractController.class);

    @Autowired
    PaginationService paginationService;

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobPostService jobPostService;

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders headers;

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response create(final JobContractDto jobContractDto) {
        Locale locale = headers.getAcceptableLanguages().get(0);
        String webpageUrl = uriInfo.getAbsolutePathBuilder().replacePath(null)
                .build().toString();

        jobContractControllerLogger.debug("Creating contract fo package {} with data: client id:{}, description:{}",
                jobContractDto.getJobPackage().getId(), jobContractDto.getClientId(), jobContractDto.getDescription());
        JobContractWithImage jobContract = jobContractService
                .create(jobContractDto.getClientId(), jobContractDto.getJobPackage().getId(),
                        jobContractDto.getDescription(), jobContractDto.getScheduledDate(), locale, webpageUrl);

        final URI contractUri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(jobContract.getId())).build();
        return Response.created(contractUri).build();
    }

    @PUT
    @Path("/{contractType}")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response updateContract(final JobContractDto jobContractDto,
                                @PathParam(value = "contractType") String contractType) {
        Locale locale = headers.getAcceptableLanguages().get(0);
        String webPageUrl = uriInfo.getAbsolutePathBuilder().replacePath(null)
                .build().toString();

        jobContractService.changeContractScheduledDate(jobContractDto.getId(),
                jobContractDto.getScheduledDate(), contractType.equals("professional"),
                locale);
        jobContractService.changeContractState(jobContractDto.getId(),
                JobContract.ContractState.values()[Math.toIntExact(jobContractDto.getState().getId())],
                headers.getAcceptableLanguages().get(0), webPageUrl);

        final URI contractUri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(jobContractDto.getId())).build();
        return Response.accepted(contractUri).build();
    }

    @GET
    @Path("/{id}/")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") final long id) {
        jobContractControllerLogger.debug("Finding job contract by id: {}", id);
        JobContract jobContract = jobContractService.findById(id);
        return Response.ok(JobContractDto.fromJobContract(
                jobContract, uriInfo))
                .build();
    }

}
