package ar.edu.itba.paw.webapp.exceptionmappers;

import org.glassfish.jersey.server.ParamException;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ParamExceptionMapper implements ExceptionMapper<ParamException> {

    @Override
    public Response toResponse(ParamException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(
                new GenericEntity<ParamExceptionMapper.HirenetParamException>(
                        new ParamExceptionMapper.HirenetParamException(e)) {
                }).build();
    }

    private static class HirenetParamException {
        private String paramName;
        private String message;

        public HirenetParamException(ParamException e) {
            this.message = String.format("Parameter error at %s: %s", e.getParameterName(), e.getMessage());
            this.paramName = e.getParameterName();
        }

        public HirenetParamException() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getParamName() {
            return paramName;
        }

        public void setParamName(String paramName) {
            this.paramName = paramName;
        }
    }
}
