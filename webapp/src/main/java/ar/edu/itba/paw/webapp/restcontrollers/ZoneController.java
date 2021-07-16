package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.dto.JobPostZoneDto;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("/zones")
@Component
public class ZoneController {

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getZones(){
        List<JobPostZoneDto> zones = Arrays.stream(JobPost.Zone.values()).map(JobPostZoneDto::fromJobPostZone).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<JobPostZoneDto>>(zones){}).build();
    }

    @Path("/{id}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getZoneById(@PathParam("id") int id){
        if(id < 0 || id > JobPost.Zone.values().length -1)
            throw new IllegalArgumentException();
        JobPostZoneDto zone =JobPostZoneDto.fromJobPostZone(JobPost.Zone.values()[id]);
        return Response.ok(new GenericEntity<JobPostZoneDto>(zone){}).build();
    }
}
