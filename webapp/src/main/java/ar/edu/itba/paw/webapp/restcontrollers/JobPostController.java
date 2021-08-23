package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.UpdateFailException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.dto.input.EditJobPackageDto;
import ar.edu.itba.paw.webapp.dto.input.EditJobPostDto;
import ar.edu.itba.paw.webapp.dto.input.NewJobPackageDto;
import ar.edu.itba.paw.webapp.dto.input.NewJobPostDto;
import ar.edu.itba.paw.webapp.dto.output.*;
import ar.edu.itba.paw.webapp.utils.ImagesUtil;
import ar.edu.itba.paw.webapp.utils.LocaleResolverUtil;
import ar.edu.itba.paw.webapp.utils.PageResponseUtil;
import ar.edu.itba.paw.webapp.validation.ValidImage;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@Path("/job-posts")
public class JobPostController {
    private final Logger jobPostControllerLogger = LoggerFactory.getLogger(JobPostController.class);

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobPostImageService jobPostImageService;

    @Autowired
    private MessageSource messageSource;

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders headers;

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createJobPost(@Valid final NewJobPostDto newJobPostDto) {
        String proEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        long proId = userService.findByEmail(proEmail).orElseThrow(UserNotFoundException::new).getId();
        String title = newJobPostDto.getTitle();
        String availableHours = newJobPostDto.getAvailableHours();
        int jobType = newJobPostDto.getJobType();
        List<Integer> zones = Arrays.stream(newJobPostDto.getZones()).boxed().collect(Collectors.toList());

        jobPostControllerLogger.debug("Creating job post with data: title: {}, available hours: {}, job type: {}, zones: {}", title, availableHours, jobType, zones);
        Long newJobPostId = jobPostService.create(proId, title, availableHours, jobType, zones).getId();

        final URI packageUri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newJobPostId)).build();
        return Response.created(packageUri).build();
    }

    @PUT
    @Path("/{id}/")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response editJobPost(@Valid final EditJobPostDto editJobPostDto,
                                @PathParam("id") final long id) {
        String title = editJobPostDto.getTitle();
        String availableHours = editJobPostDto.getAvailableHours();
        int jobType = editJobPostDto.getJobType();
        List<Integer> zones = Arrays.stream(editJobPostDto.getZones()).boxed().collect(Collectors.toList());
        boolean isActive = editJobPostDto.isActive();

        jobPostControllerLogger.debug("Updating post {} with data: title: {}, available hours: {}, job type: {}, zones: {}, isActive: {}", id, title, availableHours, jobType, zones, isActive);
        if (!jobPostService.updateJobPost(id, title, availableHours, jobType, zones, isActive)) {
            jobPostControllerLogger.debug("Error updating job post {}", id);
            throw new UpdateFailException();
        }

        final URI packageUri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(id)).build();
        return Response.created(packageUri).build();
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findAll(@QueryParam("page") @DefaultValue("1") final int page) {
        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        jobPostControllerLogger.debug("Finding al jobPosts for page {}", page);
        int maxPage = jobPostService.findAllMaxPage();
        List<JobPostDto> jobPostDtoList = jobPostService.findAll(page).stream()
                .map(jobPost -> JobPostDto.fromJobPostWithLocalizedMessage(jobPost,
                        jobPostImageService.getImagesIdsByPostId(jobPost.getId()), uriInfo,
                        messageSource.getMessage(jobPost.getJobType().getDescription(), null, locale)))
                .collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<JobPostDto>>(jobPostDtoList) {
                }));
    }

    @GET
    @Path("/{id}/")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response jobPostDetails(@PathParam("id") final long id) {
        jobPostControllerLogger.debug("Finding job post by id: {}", id);
        JobPost jobPost = jobPostService.findByIdWithInactive(id);
        List<Long> images = jobPostImageService.getImagesIdsByPostId(id);
        return Response.ok(JobPostDto.fromJobPostWithLocalizedMessage(
                        jobPost, images, uriInfo,
                        messageSource.getMessage(jobPost.getJobType().getDescription(), null, LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages()))))
                .build();
    }

    @Path("/{postId}/images")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response jobPostImages(@PathParam("postId") final long postId) {
        List<URI> uris = jobPostImageService.getImagesIdsByPostId(postId).stream().map(id -> uriInfo.getBaseUriBuilder()
                        .path("/job-posts").path(String.valueOf(postId)).path("/images").path(String.valueOf(id)).build())
                .collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<URI>>(uris) {
        }).build();
    }


    @Path("/{postId}/images")
    @POST
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response uploadPostImage(@PathParam("postId") final long postId,
                                    @Valid @NotNull @ValidImage @FormDataParam("file") final FormDataBodyPart body) {
        JobPostImage image;
        try {
            image = jobPostImageService.addImage(postId, ImagesUtil.fromInputStream(body));
        } catch (IOException e) {
            throw new RuntimeException("Upload failed");
        }
        return Response.created(uriInfo.getBaseUriBuilder().path("/job-posts").path(String.valueOf(postId)).path("/images").path(String.valueOf(image.getImageId())).build()).build();
    }

    @Path("/{postId}/images/{imageId}")
    @GET
    @Produces(value = {"image/png", "image/jpg", "image/jpeg", MediaType.APPLICATION_JSON})
    public Response getPostImage(@PathParam("postId") final long postId,
                                 @PathParam("imageId") final long imageId) {
        JobPostImage jobPostImage = jobPostImageService.findById(imageId, postId);
        return ImagesUtil.sendUnconditionalCacheImageResponse(jobPostImage.getByteImage());
    }

    @Path("/{postId}/packages")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response packagesByPostId(@PathParam("postId") final long postId,
                                     @QueryParam("type") final String type,
                                     @QueryParam("page") @DefaultValue("1") final int page) {
        jobPostControllerLogger.debug("Finding packages for post: {}", postId);
        int maxPage;
        List<JobPackage> jobPackages;
        if (type != null && type.equalsIgnoreCase("active")) {
            jobPackages = jobPackageService.findByPostIdOnlyActive(postId, page - 1);
            maxPage = jobPackageService.findByPostIdOnlyActiveMaxPage(postId);
        } else {
            jobPackages = jobPackageService.findByPostId(postId, page - 1);
            maxPage = jobPackageService.findByPostIdMaxPage(postId);
        }
        final List<JobPackageDto> packageDtoList = jobPackages
                .stream().map(pack -> JobPackageDto.fromJobPackage(pack, uriInfo))
                .collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<JobPackageDto>>(packageDtoList) {
                }));
    }

    @POST
    @Path("/{postId}/packages")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createJobPackage(@Valid final NewJobPackageDto newJobPackageDto,
                                     @PathParam("postId") final long postId) {
        String title = newJobPackageDto.getTitle();
        String description = newJobPackageDto.getDescription();
        String price = newJobPackageDto.getPrice();
        long rateType = newJobPackageDto.getRateType();
        jobPostControllerLogger.debug(
                "Creating package for post {} with data: title: {}, description: {}, price: {}, rate type:{}",
                postId, title, description, price, rateType);
        JobPackage jobPackage = jobPackageService.create(postId, title, description, price, rateType);

        final URI packageUri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(jobPackage.getId())).build();
        return Response.created(packageUri).build();
    }

    @PUT
    @Path("/{postId}/packages/{packageId}")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response editJobPackage(@Valid final EditJobPackageDto editJobPackageDto,
                                   @PathParam("postId") final long postId,
                                   @PathParam("packageId") final long packageId) {
        String title = editJobPackageDto.getTitle();
        String description = editJobPackageDto.getDescription();
        String price = editJobPackageDto.getPrice();
        Integer rateType = editJobPackageDto.getRateType();
        Boolean isActive = null;
        if (editJobPackageDto.getIsActive().equalsIgnoreCase("false"))
            isActive = false;
        else if (editJobPackageDto.getIsActive().equalsIgnoreCase("true"))
            isActive = true;

        jobPostControllerLogger.debug(
                "Updating package {} with data: title: {}, description: {}, price: {}, rate type: {}, isActive: {}",
                packageId, title, description, price, rateType, isActive);
        if (!jobPackageService.updateJobPackage(packageId, postId, title, description, price, rateType, isActive)) {
            jobPostControllerLogger.debug("Package not found {}", packageId);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final URI packageUri = uriInfo.getAbsolutePathBuilder().build();
        return Response.ok(packageUri).build();
    }

    @Path("/{postId}/packages/{packageId}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response packagesById(@PathParam("postId") final long postId,
                                 @PathParam("packageId") final long packageId) {
        jobPostControllerLogger.debug("Finding packages by Id: {}", packageId);
        final JobPackage jobPackage = jobPackageService.findById(packageId, postId);
        final JobPackageDto packageDto = JobPackageDto.fromJobPackage(jobPackage, uriInfo);

        return Response.ok(new GenericEntity<JobPackageDto>(packageDto) {
        }).build();
    }

}
