package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.PaginationService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.UserAlreadyExistsException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.dto.JobContractCardDto;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.utils.ImageUploadUtil;
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

@Component
@Path("/users")
public class UserController {
    private final Logger accountControllerLogger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private PaginationService paginationService;

    @Autowired
    private MessageSource messageSource;


    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders headers;

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response register(@Valid final UserDto userDto) {
        String webpageUrl = uriInfo.getAbsolutePathBuilder().replacePath(null)
                .build().toString();

        User currentUser;
        try {
            accountControllerLogger.debug("Registering user with data: email: {}, password: {}, name: {}, phone: {}",
                    userDto.getEmail(), userDto.getPassword(), userDto.getUsername(), userDto.getPhone());
            currentUser = userService.register(userDto.getEmail(), userDto.getPassword(), userDto.getUsername(), userDto.getPhone(),
                    null, headers.getAcceptableLanguages().get(0), webpageUrl);
        } catch (UserAlreadyExistsException e) {
            accountControllerLogger.error("Register error: email already exists");
            return Response.status(Response.Status.CONFLICT).build();
        }
        final URI userUri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(currentUser.getId()))
                .build();
        return Response.created(userUri).build();
    }

    @Path("/{id}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") long id) {
        accountControllerLogger.debug("Finding user with email {}", id);
        UserWithImage user = userService.findUserWithImage(id);
        accountControllerLogger.debug("Finding auth info for user with email {}", user.getEmail());
        UserAuth auth = userService.getAuthInfo(user.getEmail()).orElseThrow(UserNotFoundException::new);

        UserDto userAnswer = UserDto.fromUserAndRoles(user, auth.getRoles(),uriInfo);
        return Response.ok(userAnswer).build();
    }

    @Path("/{id}/contracts")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getHiredContracts(@PathParam("id") long id,
                                      @QueryParam(value = "state") final String contractState,
                                      @QueryParam(value = "page") @DefaultValue("1") final int page) {
        if (!contractState.equals("active") && !contractState.equals("pending") && !contractState.equals("finalized"))
            throw new IllegalArgumentException();

        List<JobContract.ContractState> states = jobContractService.getContractStates(contractState);
        Locale locale = headers.getAcceptableLanguages().get(0);
        int maxPage = paginationService.findContractsByClientIdMaxPage(id, states);
        List<JobContractCardDto> jobContractCardDtoList = jobContractService
                .findJobContractCardsByClientIdAndSorted(id, states, page - 1, locale).stream()
                .map(jobContractCard -> JobContractCardDto.fromJobContractCardWithLocalizedMessage(
                        jobContractCard, uriInfo, true,
                        messageSource.getMessage(jobContractCard.getJobCard().getJobPost().getJobType().getDescription(),null,locale
                        )
                ))
                .collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<JobContractCardDto>>(jobContractCardDtoList) {
                }));
    }

    @Path("/{id}/image")
    @GET
    @Produces(value = {"image/png", "image/jpg",MediaType.APPLICATION_JSON})
    public Response getPostImage(@PathParam("id") final long id) {
        ByteImage byteImage = userService.findImageByUserId(id);
        return Response.ok(new ByteArrayInputStream(byteImage.getData())).build();
    }

    @Path("/{id}/image")
    @PUT
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response uploadUserImage(@PathParam("id") long id,
                                    @FormDataParam("file") FormDataBodyPart body) {

        long result;
        try {
            result = userService.updateUserImage(id, ImageUploadUtil.fromInputStream(body));
        } catch (IOException e) {
            throw new RuntimeException("Upload failed");
        }
        if(result == -1)
            throw new RuntimeException("Couldn't save image");
        return Response.created(uriInfo.getBaseUriBuilder().path("/users").path(String.valueOf(id)).path("/image").build()).build();
    }

    @Path("/search/")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getByEmail(@QueryParam("email") String email) {
        accountControllerLogger.debug("Finding user with email {}", email);
        UserWithImage currentUser = userService.findUserWithImageByEmail(email).orElseThrow(UserNotFoundException::new);
        accountControllerLogger.debug("Finding auth info for user with email {}", currentUser.getEmail());
        UserAuth auth = userService.getAuthInfo(currentUser.getEmail()).orElseThrow(UserNotFoundException::new);

        UserDto userAnswer = UserDto.fromUserAndRoles(currentUser, auth.getRoles(),uriInfo);
        return Response.ok(userAnswer).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response changeData(final UserDto userDto, @PathParam("id") final long id) {

        accountControllerLogger.debug("Finding user with id {}", id);
        User user = userService.findById(id);
        accountControllerLogger.debug("Finding auth info for user with email {}", user.getEmail());
        UserAuth auth = userService.getAuthInfo(user.getEmail()).orElseThrow(UserNotFoundException::new);

        String username = userDto.getUsername() != null ? userDto.getUsername() : user.getUsername();
        String phone = userDto.getPhone() != null ? userDto.getPhone() : user.getPhone();
        accountControllerLogger.debug("Updating user {} with data: name: {}, phone: {}", id,
                username, phone);
        UserWithImage updatedUser = userService.updateUserById(id, username, phone);

        UserDto userAnswer = UserDto.fromUserAndRoles(updatedUser, auth.getRoles(),uriInfo);
        return Response.ok(userAnswer).build();
    }

    @Path("/{id}/security")
    @PUT
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response changePassword(final UserDto user, @PathParam("id") final long id) {

        accountControllerLogger.debug("Updating user password with id {}", id);

        userService.changeUserPassword(id, user.getPassword());

        return Response.ok(userService.findById(id)).build();
    }
}

