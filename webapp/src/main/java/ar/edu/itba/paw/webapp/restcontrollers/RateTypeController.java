package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.webapp.dto.output.JobPackageRateTypeDto;
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

@Component
@Path("/rate-types")
public class RateTypeController {

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getRateTypes(@Context Request request){
        List<JobPackage.RateType> rateTypes = Arrays.asList(JobPackage.RateType.values());
        List<JobPackageRateTypeDto> packageRateTypeDtos = rateTypes.stream().map(JobPackageRateTypeDto::fromJobPackageRateType).collect(Collectors.toList());
        GenericEntity<List<JobPackageRateTypeDto>> entity = new GenericEntity<List<JobPackageRateTypeDto>>(packageRateTypeDtos){};
        return CacheUtils.sendConditionalCacheResponse(request, entity, new EntityTag(Integer.toString(rateTypes.hashCode())));
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getRateTypeById(@PathParam("id") int id,@Context Request request){
        JobPackage.RateType rateType = JobPackage.RateType.values()[id];
        JobPackageRateTypeDto packageRateTypeDto = JobPackageRateTypeDto.fromJobPackageRateType(rateType);
        GenericEntity<JobPackageRateTypeDto> entity =  new GenericEntity<JobPackageRateTypeDto>(packageRateTypeDto){};
        return CacheUtils.sendConditionalCacheResponse(request, entity, new EntityTag(Integer.toString(rateType.hashCode())));
    }
}
