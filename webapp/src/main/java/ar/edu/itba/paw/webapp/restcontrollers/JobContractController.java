package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobContractWithImage;
import ar.edu.itba.paw.webapp.dto.input.EditJobContractDto;
import ar.edu.itba.paw.webapp.dto.input.NewJobContractDto;
import ar.edu.itba.paw.webapp.dto.output.JobContractCardDto;
import ar.edu.itba.paw.webapp.dto.output.JobContractDto;
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

import javax.validation.Valid;
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
                                  @QueryParam("state") final String contractState,
                                  @QueryParam("role") final String role,
                                  @QueryParam("page") @DefaultValue("1") int page) {
        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        JobContractsControllerLogger.debug("Finding contracts Max page {}", userId);
        int maxPage = jobContractService.findContractsMaxPage(userId, contractState, role);

        List<JobContractCardDto> jobContractCardDtoList = jobContractService.findContracts(userId, contractState, role, page)
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
    public Response createJobContract(final NewJobContractDto newContract) {
        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        String webpageUrl = uriInfo.getAbsolutePathBuilder().replacePath(null)
                .build().toString();

        JobContractsControllerLogger.debug("Creating contract for package {} with data: client id:{}, description:{}",
                newContract.getJobPackageId(), newContract.getClientId(), newContract.getDescription());
        JobContractWithImage jobContract = jobContractService.create(
                newContract.getClientId(), newContract.getJobPackageId(),
                newContract.getDescription(), newContract.getScheduledDate(), locale, webpageUrl);

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
    public Response updateContract(@Valid final EditJobContractDto editJobContractDto,
                                   @PathParam("contractId") final long contractId,
                                   @QueryParam("role") final String role) {
        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        String webPageUrl = uriInfo.getAbsolutePathBuilder().replacePath(null)
                .build().toString();

        boolean isPro;
        if (role.equals("professional"))
            isPro = true;
        else if (role.equals("client"))
            isPro = false;
        else throw new IllegalArgumentException("Invalid role type in query param");

        if (editJobContractDto.getNewScheduledDate() == null)
            JobContractsControllerLogger.debug("Updating job contract id: {} from {} with data: state: {}",
                    contractId, isPro ? "professional" : "client", editJobContractDto.getNewState());
        else
            JobContractsControllerLogger.debug("Updating job contract id: {} from {} with data: scheduledDate: {}, state: {}",
                    contractId, isPro ? "professional" : "client", editJobContractDto.getNewScheduledDate(),
                    editJobContractDto.getNewState());

        jobContractService.changeContractScheduledDate(contractId,
                editJobContractDto.getNewScheduledDate(), isPro,
                locale);
        jobContractService.changeContractState(contractId,
                JobContract.ContractState.values()[Math.toIntExact(editJobContractDto.getNewState())],
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
                                        @FormDataParam("file") final FormDataBodyPart body) {
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
