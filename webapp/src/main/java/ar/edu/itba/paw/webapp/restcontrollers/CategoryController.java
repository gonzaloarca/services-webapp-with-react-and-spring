package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.dto.JobTypeDto;
import ar.edu.itba.paw.webapp.utils.LocaleResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

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
    public Response getCategories() {
        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        List<JobTypeDto> jobTypeDtoList = Arrays.stream(JobPost.JobType.values())
                .map(jobType -> JobTypeDto
                        .fromJobTypeWithLocalizedMessage(jobType,
                                messageSource.getMessage(jobType.getDescription(),
                                        null, locale))).collect(Collectors.toList()
                );
        return Response.ok(new GenericEntity<List<JobTypeDto>>(jobTypeDtoList) {
        }).build();
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Response getCategory(@PathParam("id") int id) {
        if (id < 0 || id > JobPost.JobType.values().length - 1)
            throw new IllegalArgumentException();
        Locale locale = LocaleResolverUtil.resolveLocale(headers.getAcceptableLanguages());
        JobTypeDto jobTypeDto = JobTypeDto.fromJobTypeWithLocalizedMessage(JobPost.JobType.values()[id],
                messageSource.getMessage(JobPost.JobType.values()[id].getDescription(), null, locale));
        return Response.ok(new GenericEntity<JobTypeDto>(jobTypeDto) {
        }).build();
    }

}
