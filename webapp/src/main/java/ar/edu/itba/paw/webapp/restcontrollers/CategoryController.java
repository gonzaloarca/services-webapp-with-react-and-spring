package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.dto.output.JobTypeDto;
import ar.edu.itba.paw.webapp.utils.CacheUtils;
import ar.edu.itba.paw.webapp.utils.LocaleResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Path("/categories")
@Component
public class CategoryController {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private MessageSource messageSource;

    @Context
    private HttpHeaders headers;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getCategories(@Context Request request) {
        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        List<JobPost.JobType> jobTypes = Arrays.asList(JobPost.JobType.values());
        List<JobTypeDto> jobTypeDtoList = jobTypes.stream()
                .map(jobType -> JobTypeDto
                        .fromJobTypeWithLocalizedMessage(jobType,
                                messageSource.getMessage(jobType.getDescription(),
                                        null, locale))).collect(Collectors.toList()
                );

        GenericEntity<List<JobTypeDto>> entity = new GenericEntity<List<JobTypeDto>>(jobTypeDtoList) {
        };
        return CacheUtils.sendConditionalCacheResponse(request, entity, new EntityTag(Integer.toString(jobTypes.hashCode())));


    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Response getCategory(@PathParam("id") int id, @Context Request request) {
        if (id < 0 || id > JobPost.JobType.values().length - 1)
            throw new IllegalArgumentException();
        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        JobPost.JobType jobType = JobPost.JobType.values()[id];
        JobTypeDto jobTypeDto = JobTypeDto.fromJobTypeWithLocalizedMessage(jobType,
                messageSource.getMessage(JobPost.JobType.values()[id].getDescription(), null, locale));
        GenericEntity<JobTypeDto> entity = new GenericEntity<JobTypeDto>(jobTypeDto) {
        };
        return CacheUtils.sendConditionalCacheResponse(request, entity, new EntityTag(Integer.toString(jobType.hashCode())));
    }

}
