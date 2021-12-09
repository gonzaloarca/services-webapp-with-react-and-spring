package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.webapp.dto.output.JobPostZoneDto;
import ar.edu.itba.paw.webapp.utils.CacheUtils;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("/zones")
@Component
public class ZoneController {

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getZones(@Context Request request) {
        List<JobPost.Zone> zones = Arrays.asList(JobPost.Zone.values());

        List<JobPostZoneDto> zoneDtos = zones.stream().map(JobPostZoneDto::fromJobPostZone).collect(Collectors.toList());

        GenericEntity<List<JobPostZoneDto>> entity = new GenericEntity<List<JobPostZoneDto>>(zoneDtos) {};
        return CacheUtils.sendConditionalCacheResponse(request, entity, new EntityTag(Integer.toString(zones.hashCode())));
    }

    @Path("/{id}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getZoneById(@PathParam("id") int id,@Context Request request) {
        if (id < 0 || id > JobPost.Zone.values().length - 1)
            throw new IllegalArgumentException();
        JobPost.Zone zone = JobPost.Zone.values()[id];
        JobPostZoneDto zoneDto = JobPostZoneDto.fromJobPostZone(JobPost.Zone.values()[id]);
        GenericEntity<JobPostZoneDto> entity = new GenericEntity<JobPostZoneDto>(zoneDto) {};
        return CacheUtils.sendConditionalCacheResponse(request, entity, new EntityTag(Integer.toString(zone.hashCode())));
    }
}
