package ar.edu.itba.paw.webapp.restcontrollers;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.NoSuchElementException;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NoSuchElementException> {

    @Override
    public Response toResponse(NoSuchElementException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(new GenericEntity<HirenetNotFoundErrorMessage>(new HirenetNotFoundErrorMessage(e)){}).build();
    }

    private static class HirenetNotFoundErrorMessage{
        private String message;

        public HirenetNotFoundErrorMessage(NoSuchElementException e){
            this.message = e.getMessage();
        }

        public HirenetNotFoundErrorMessage() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
