package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.dto.JobContractDto;
import ar.edu.itba.paw.webapp.dto.JobPostDto;
import ar.edu.itba.paw.webapp.utils.LocaleResolverUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Component
@Path("/job-contracts")
public class JobContractController {
    private final Logger JobContractControllerLogger = LoggerFactory.getLogger(JobContractController.class);

    @Autowired
    PaginationService paginationService;

    @Autowired
    private JobContractService jobContractService;

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders headers;

    @GET
    @Path("/{id}/")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") final long id) {
        JobContractControllerLogger.debug("Finding job contract by id: {}", id);
        JobContract jobContract = jobContractService.findById(id);
        return Response.ok(JobContractDto.fromJobContract(
                jobContract, uriInfo))
                .build();
    }

}
