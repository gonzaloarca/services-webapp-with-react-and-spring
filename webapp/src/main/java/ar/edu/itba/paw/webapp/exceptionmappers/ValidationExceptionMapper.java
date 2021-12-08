package ar.edu.itba.paw.webapp.exceptionmappers;

import org.glassfish.jersey.server.validation.ValidationError;
import org.glassfish.jersey.server.validation.internal.ValidationHelper;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper
        implements ExceptionMapper<ConstraintViolationException> {

    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(ConstraintViolationException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(
                new GenericEntity<>(
                        ValidationHelper.constraintViolationToValidationErrors(e).stream()
                                .map(HirenetValidationErrorMessage::new).collect(Collectors.toList()),
                        new GenericType<List<HirenetValidationErrorMessage>>() {
                        }.getType()
                )).build();
    }


    private static class HirenetValidationErrorMessage{
        private String error;
        private String value;

        public HirenetValidationErrorMessage(ValidationError e){
            String[] path = e.getPath().split("\\.");
            this.error = e.getMessage();
            this.value = e.getInvalidValue();
        }

        public HirenetValidationErrorMessage() {
        }

        public String getError() {
            return error;
        }

        public String getValue() {
            return value;
        }

        public void setError(String error) {
            this.error = error;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}