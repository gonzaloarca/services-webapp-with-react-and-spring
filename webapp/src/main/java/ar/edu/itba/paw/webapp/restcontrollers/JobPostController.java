package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.dto.ImageDto;
import ar.edu.itba.paw.models.exceptions.UpdateFailException;
import ar.edu.itba.paw.webapp.dto.*;
import ar.edu.itba.paw.webapp.form.PackageForm;
import ar.edu.itba.paw.webapp.utils.ImageUploadUtil;
import ar.edu.itba.paw.webapp.utils.LocaleResolverUtil;
import ar.edu.itba.paw.webapp.utils.PageResponseUtil;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.net.URI;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@Path("/job-posts")
public class JobPostController {
    private final Logger jobPostControllerLogger = LoggerFactory.getLogger(JobPostController.class);

    @Autowired
    PaginationService paginationService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JobPackageService jobPackageService;

    @Autowired
    private JobPostImageService jobPostImageService;

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private MessageSource messageSource;

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders headers;

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createJobPost(final JobPostDto jobPostDto) {
        long proId = jobPostDto.getProfessional().getId();
        String title = jobPostDto.getTitle();
        String availableHours = jobPostDto.getAvailableHours();
        int jobType = jobPostDto.getJobType().getId();
        List<Integer> zones = jobPostDto.getZones().stream().map(JobPostZoneDto::getId).collect(Collectors.toList());

        jobPostControllerLogger.debug("Creating job post with data: email: {}, title: {}, available hours: {}, job type: {}, zones: {}", proId, title, availableHours, jobType, zones);
        JobPost newJobPost = jobPostService.create(proId, title, availableHours, jobType, zones);

        final URI packageUri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(newJobPost.getId())).build();
        return Response.created(packageUri).build();
    }

    @PUT
    @Path("/{id}/")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response editJobPost(final JobPostDto jobPostDto, @PathParam("id") final long id) {
        String title = jobPostDto.getTitle();
        String availableHours = jobPostDto.getAvailableHours();
        int jobType = jobPostDto.getJobType().getId();
        List<Integer> zones = jobPostDto.getZones().stream().map(JobPostZoneDto::getId).collect(Collectors.toList());
        boolean isActive = jobPostDto.getActive();

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
    @Path("/{id}/")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response jobPostDetails(@PathParam("id") final long id) {
        jobPostControllerLogger.debug("Finding job post by id: {}", id);
        JobPost jobPost = jobPostService.findByIdWithInactive(id);
        return Response.ok(JobPostDto.fromJobPostWithLocalizedMessage(
                jobPost, uriInfo,
                messageSource.getMessage(jobPost.getJobType().getDescription(), null, LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages()))))
                .build();
    }

    @Path("/{postId}/images")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response jobPostImages(@PathParam("postId") long postId) {
        List<Long> ids = jobPostImageService.getImagesIdsByPostId(postId);
        List<ImageDto> uris = ids.stream().map(id -> ImageDto.fromImageId(id, postId, uriInfo)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<ImageDto>>(uris) {
        }).build();
    }


    @Path("/{postId}/images")
    @POST
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response uploadPostImage(@PathParam("postId") long postId) {
        InputStream file = new ByteArrayInputStream(null);
        JobPostImage image;
        try {
            image = jobPostImageService.addImage(postId, ImageUploadUtil.fromInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException("Upload failed");
        }
        return Response.created(uriInfo.getBaseUriBuilder().path("/job-posts").path(String.valueOf(postId)).path("/images").path(String.valueOf(image.getImageId())).build()).build();
    }

    @Path("/{postId}/images/{id}")
    @GET
    @Produces(value = {"image/png", "image/jpg"})
    public Response getPostImage(@PathParam("postId") final long postId, @PathParam("id") final long imageId) {
        JobPostImage jobPostImage = jobPostImageService.findById(imageId, postId);
        return Response.ok(new ByteArrayInputStream(jobPostImage.getByteImage().getData())).build();
    }


    @GET
    @Path("/{id}/reviews")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response reviewsByPostId(
            @PathParam("id") final long id,
            @QueryParam(value = "page") @DefaultValue("1") int page) {
        if (page < 1) {
            page = 1;
        }

        jobPostControllerLogger.debug("Finding reviews for post: {}", id);
        int maxPage = paginationService.findReviewsByPostIdMaxPage(id);

        final List<ReviewDto> reviewDtoList = reviewService.findReviewsByPostId(id, page - 1)
                .stream().map(review -> ReviewDto.fromReview(review, uriInfo)).collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<ReviewDto>>(reviewDtoList) {
                }));
    }

    @Path("/{id}/packages")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response packagesByPostId(
            @PathParam("id") final long id,
            @QueryParam(value = "page") @DefaultValue("1") int page) {
        if (page < 1) {
            page = 1;
        }
        jobPostControllerLogger.debug("Finding packages for post: {}", id);
        int maxPage = paginationService.findJobPackageByPostIdMaxPage(id);
        final List<JobPackageDto> packageDtoList = jobPackageService.findByPostId(id, page - 1)
                .stream().map(pack -> JobPackageDto.fromJobPackage(pack, uriInfo))
                .collect(Collectors.toList());

        return PageResponseUtil.getGenericListResponse(page, maxPage, uriInfo,
                Response.ok(new GenericEntity<List<JobPackageDto>>(packageDtoList) {
                }));
    }

    @POST
    @Path("/{postId}/packages")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createJobPackage(final JobPackageDto jobPackageDto, @PathParam("postId") final long postId) {
        String title = jobPackageDto.getTitle();
        String description = jobPackageDto.getDescription();
        String price = jobPackageDto.getPrice().toString();
        long rateType = jobPackageDto.getRateType().getId();
        jobPostControllerLogger.debug("Creating package for post {} with data: title: {}, description: {}, price: {}, rate type:{}", postId, title, description, price, rateType);
        JobPackage jobPackage = jobPackageService.create(postId, title, description, price, rateType);

        final URI packageUri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(jobPackage.getId())).build();
        return Response.created(packageUri).build();
    }

    @PUT
    @Path("/{postId}/packages/{packageId}")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response editJobPackage(final JobPackageDto jobPackageDto,
                                   @PathParam("postId") final long postId,
                                   @PathParam("packageId") final long packageId) {
        String title = jobPackageDto.getTitle();
        String description = jobPackageDto.getDescription();
        String price = jobPackageDto.getPrice().toString();
        long rateType = jobPackageDto.getRateType().getId();
        boolean isActive = jobPackageDto.isActive();

        jobPostControllerLogger.debug("Updating package {} with data: title: {}, description: {}, price: {}, rate type: {}, isActive: {}",
                packageId, title, description, price, rateType, isActive);
        if (!jobPackageService.updateJobPackage(packageId, postId, title, description, price, rateType, isActive)) {
            jobPostControllerLogger.debug("Package not found {}", packageId);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final URI packageUri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(packageId)).build();
        return Response.ok(packageUri).build();
    }

    @Path("/{postId}/packages/{packageId}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response packagesById(
            @PathParam("postId") final long postId,
            @PathParam("packageId") final long packageId) {
        jobPostControllerLogger.debug("Finding packages by Id: {}", packageId);
        final JobPackage jobPackage = jobPackageService.findById(packageId, postId);
        final JobPackageDto packageDto = JobPackageDto.fromJobPackage(jobPackage, uriInfo);

        return Response.ok(new GenericEntity<JobPackageDto>(packageDto) {
        }).build();
    }

    @POST
    @Path("/{postId}/packages/{packageId}")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createJobContract(final JobContractDto jobContractDto,
                                      @PathParam("postId") final long postId,
                                      @PathParam("packageId") final long packageId) {
        Locale locale = headers.getAcceptableLanguages().get(0);
        String webpageUrl = uriInfo.getAbsolutePathBuilder().replacePath(null)
                .build().toString();

        jobPostControllerLogger.debug("Creating contract for package {} with data: client id:{}, description:{}",
                jobContractDto.getJobPackage().getId(), jobContractDto.getClientId(), jobContractDto.getDescription());
        JobContractWithImage jobContract = jobContractService
                .create(jobContractDto.getClientId(), jobContractDto.getJobPackage().getId(),
                        jobContractDto.getJobPackage().getJobPost().getId(),
                        jobContractDto.getDescription(), jobContractDto.getScheduledDate(), locale, webpageUrl);

        final URI contractUri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(jobContract.getId())).build();
        return Response.created(contractUri).build();
    }

    @Path("/{postId}/packages/{packageId}/contracts")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response contractsByPackageId(
            @PathParam("postId") final long postId,
            @PathParam("packageId") final long packageId,
            @QueryParam("page") @DefaultValue("1") int page) {
        if (page < 1)
            page = 1;
        jobPostControllerLogger.debug("Finding contracts by package id: {}", packageId);
        List<JobContractDto> packages = jobContractService.findByPackageId(packageId, postId, page - 1).stream().map(jobContract -> JobContractDto.fromJobContract(jobContract, uriInfo)).collect(Collectors.toList());
        int maxPage = jobContractService.findByPackageIdMaxPage(packageId, postId);
        return PageResponseUtil.getGenericListResponse(page, maxPage, uriInfo, Response.ok(new GenericEntity<List<JobContractDto>>(packages) {
        }));
    }

    @PUT
    @Path("/{postId}/packages/{packageId}/contracts/{contractId}/{contractType}")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response updateContract(final JobContractDto jobContractDto,
                                   @PathParam("postId") final long postId,
                                   @PathParam("packageId") final long packageId,
                                   @PathParam("contractId") final long contractId,
                                   @PathParam(value = "contractType") String contractType) {
        Locale locale = headers.getAcceptableLanguages().get(0);
        String webPageUrl = uriInfo.getAbsolutePathBuilder().replacePath(null)
                .build().toString();

        jobContractService.findById(packageId, packageId, postId);
        jobPostControllerLogger.debug("Updating job contract  id: {} with data: scheduledDate: {}, state: {}", contractId, jobContractDto.getScheduledDate(), jobContractDto.getState());
        jobContractService.changeContractScheduledDate(jobContractDto.getId(),
                jobContractDto.getScheduledDate(), contractType.equals("professional"),
                locale);
        jobContractService.changeContractState(jobContractDto.getId(),
                JobContract.ContractState.values()[Math.toIntExact(jobContractDto.getState().getId())],
                headers.getAcceptableLanguages().get(0), webPageUrl);

        final URI contractUri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(jobContractDto.getId())).build();
        return Response.ok(contractUri).build();
    }

    @GET
    @Path("/{postId}/packages/{packageId}/contracts/{contractId}/")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("postId") final long postId,
                            @PathParam("packageId") final long packageId,
                            @PathParam("contractId") final long contractId) {
        jobPostControllerLogger.debug("Finding job contract by id: {}", contractId);
        return Response.ok(JobContractDto
                .fromJobContract(jobContractService.findById(contractId, packageId, postId), uriInfo))
                .build();
    }

    @Path("/{postId}/packages/{packageId}/contracts/{contractId}/image")
    @GET
    @Produces(value = {"image/png", "image/jpg"})
    public Response getContractImage(@PathParam("postId") final long postId,
                                     @PathParam("packageId") final long packageId,
                                     @PathParam("contractId") final long contractId) {
        ByteImage byteImage = jobContractService.findImageByContractId(contractId, packageId, postId);
        return Response.ok(new ByteArrayInputStream(byteImage.getData())).build();
    }
}
