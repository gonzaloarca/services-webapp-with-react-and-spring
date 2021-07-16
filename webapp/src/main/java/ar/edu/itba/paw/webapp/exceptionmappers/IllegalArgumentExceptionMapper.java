package ar.edu.itba.paw.webapp.exceptionmappers;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(IllegalArgumentException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new GenericEntity<HirenetIllegalArgumentMessage>(new HirenetIllegalArgumentMessage(e)) {
        }).build();
    }

    private static class HirenetIllegalArgumentMessage {
        private String message;

        public HirenetIllegalArgumentMessage(IllegalArgumentException e) {
            this.message = e.getMessage();
        }

        public HirenetIllegalArgumentMessage() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
