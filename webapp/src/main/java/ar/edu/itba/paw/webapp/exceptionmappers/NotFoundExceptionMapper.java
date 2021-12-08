package ar.edu.itba.paw.webapp.exceptionmappers;

import ar.edu.itba.paw.webapp.dto.ErrorDto;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.NoSuchElementException;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NoSuchElementException> {

    @Override
    public Response toResponse(NoSuchElementException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(
                new GenericEntity<ErrorDto>(
                        new ErrorDto(e)) {
                }).build();
    }
}
