package ar.edu.itba.paw.webapp.exceptionmappers;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException e) {
        return Response.status(Response.Status.BAD_GATEWAY).entity(
                new GenericEntity<HirenetRuntimeExceptionMessage>(
                        new HirenetRuntimeExceptionMessage(e)) {
                }).build();
    }

    private static class HirenetRuntimeExceptionMessage {
        private String message;

        public HirenetRuntimeExceptionMessage(RuntimeException e) {
            this.message = e.getMessage();
        }

        public HirenetRuntimeExceptionMessage() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
