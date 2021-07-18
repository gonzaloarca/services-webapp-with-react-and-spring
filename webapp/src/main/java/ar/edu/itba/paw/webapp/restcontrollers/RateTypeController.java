package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.webapp.dto.output.JobPackageRateTypeDto;
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

@Component
@Path("/rate-types")
public class RateTypeController {

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getRateTypes(){
        List<JobPackageRateTypeDto> packageRateTypeDtos = Arrays.stream(JobPackage.RateType.values()).map(JobPackageRateTypeDto::fromJobPackageRateType).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<JobPackageRateTypeDto>>(packageRateTypeDtos){}).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getRateTypeById(@PathParam("id") int id){
        JobPackageRateTypeDto packageRateTypeDto = JobPackageRateTypeDto.fromJobPackageRateType(JobPackage.RateType.values()[id]);
        return Response.ok(new GenericEntity<JobPackageRateTypeDto>(packageRateTypeDto){}).build();
    }
}
