package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.PaginationService;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobContractCard;
import ar.edu.itba.paw.models.JobContractWithImage;
import ar.edu.itba.paw.webapp.dto.JobCardDto;
import ar.edu.itba.paw.webapp.dto.JobContractCardDto;
import ar.edu.itba.paw.webapp.dto.JobContractDto;
import ar.edu.itba.paw.webapp.utils.ImageUploadUtil;
import ar.edu.itba.paw.webapp.utils.LocaleResolverUtil;
import ar.edu.itba.paw.webapp.utils.PageResponseUtil;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Path("/contracts")
@Component
public class JobContractController {
    private final Logger JobContractsControllerLogger = LoggerFactory.getLogger(ar.edu.itba.paw.webapp.restcontrollers.JobContractController.class);

    @Autowired
    PaginationService paginationService;

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private MessageSource messageSource;

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders headers;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findContracts(@QueryParam("userId") final Long userId,
                                  @QueryParam(value = "state") final String contractState,
                                  @QueryParam(value = "role") final String role,
                                  @QueryParam(value = "page") @DefaultValue("1") int page) {
        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        JobContractsControllerLogger.debug("Finding contractStates rankings for user {}", userId);
        int maxPage = jobContractService.findContractsMaxPage(userId, contractState, role, locale);

        List<JobContractCardDto> jobContractCardDtoList = jobContractService.findContracts(userId, contractState, role, page, locale)
                .stream().map(jobContractCard -> JobContractCardDto
                        .fromJobContractCardWithLocalizedMessage(jobContractCard, uriInfo, messageSource
                                .getMessage(jobContractCard.getJobCard().getJobPost().getJobType().getDescription(),
                                        null, locale)
                        )).collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<JobContractCardDto>>(jobContractCardDtoList) {
                }));
    }

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createJobContract(final JobContractDto jobContractDto) {
        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        String webpageUrl = uriInfo.getAbsolutePathBuilder().replacePath(null)
                .build().toString();

        JobContractsControllerLogger.debug("Creating contract for package {} with data: client id:{}, description:{}",
                jobContractDto.getJobPackage().getId(), jobContractDto.getClientId(), jobContractDto.getDescription());
        JobContractWithImage jobContract = jobContractService
                .create(jobContractDto.getClientId(), jobContractDto.getJobPackage().getId(),
                        jobContractDto.getDescription(), jobContractDto.getScheduledDate(), locale, webpageUrl);

        final URI contractUri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(jobContract.getId())).build();
        return Response.created(contractUri).build();
    }

    @GET
    @Path("/{contractId}/")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("contractId") final long contractId) {
        JobContractsControllerLogger.debug("Finding job contract by id: {}", contractId);
        return Response.ok(JobContractDto
                .fromJobContract(jobContractService.findById(contractId), uriInfo))
                .build();
    }

    @PUT
    @Path("/{contractId}")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response updateContract(final JobContractDto jobContractDto,
                                   @PathParam("contractId") final long contractId,
                                   @QueryParam(value = "role") String role) {
        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        String webPageUrl = uriInfo.getAbsolutePathBuilder().replacePath(null)
                .build().toString();

        boolean isPro;
        if (role.equals("professional"))
            isPro = true;
        else if (role.equals("client"))
            isPro = false;
        else throw new IllegalArgumentException("Invalid role type in query param");

        JobContractsControllerLogger.debug("Updating job contract  id: {} with data: scheduledDate: {}, state: {}", contractId, jobContractDto.getScheduledDate(), jobContractDto.getState());
        jobContractService.changeContractScheduledDate(contractId,
                jobContractDto.getScheduledDate(), isPro,
                locale);
        jobContractService.changeContractState(contractId,
                JobContract.ContractState.values()[Math.toIntExact(jobContractDto.getState().getId())],
                locale, webPageUrl);

        final URI contractUri = uriInfo.getAbsolutePathBuilder().build();
        return Response.ok(contractUri).build();
    }

    @Path("/{contractId}/image")
    @GET
    @Produces(value = {"image/png", "image/jpg", MediaType.APPLICATION_JSON})
    public Response getContractImage(@PathParam("contractId") final long contractId) {
        ByteImage byteImage = jobContractService.findImageByContractId(contractId);
        return Response.ok(new ByteArrayInputStream(byteImage.getData())).build();
    }

    @Path("/{contractId}/image")
    @PUT
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response uploadContractImage(@PathParam("contractId") final long contractId,
                                        @FormDataParam("file") FormDataBodyPart body) {
        try {
            if (jobContractService.addContractImage(contractId, ImageUploadUtil.fromInputStream(body)) == -1)
                throw new RuntimeException("Couldn't save image");
        } catch (IOException e) {
            throw new RuntimeException("Upload failed");
        }
        return Response.created(uriInfo.getBaseUriBuilder()
                .path("/contracts").path(String.valueOf(contractId))
                .path("/image").build()).build();
    }
}
