package ar.edu.itba.paw.webapp.exceptionmappers;

import ar.edu.itba.paw.models.exceptions.UserAlreadyExistsException;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConflictExceptionMapper implements ExceptionMapper<UserAlreadyExistsException> {
    @Override
    public Response toResponse(UserAlreadyExistsException e) {
        return Response.status(Response.Status.CONFLICT).entity(
                new GenericEntity<UserAlreadyExistsExceptionMessage>(
                        new UserAlreadyExistsExceptionMessage(e)) {
                }).build();
    }

    private static class UserAlreadyExistsExceptionMessage {
        private String message;

        public UserAlreadyExistsExceptionMessage(RuntimeException e) {
            this.message = e.getMessage();
        }

        public UserAlreadyExistsExceptionMessage() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
