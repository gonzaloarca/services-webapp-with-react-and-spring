package ar.edu.itba.paw.webapp.exceptionmappers;

import ar.edu.itba.paw.webapp.dto.ErrorDto;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException e) {
        return Response.status(Response.Status.BAD_GATEWAY).entity(
                new GenericEntity<ErrorDto>(
                        new ErrorDto(e)) {
                }).build();
    }
}
