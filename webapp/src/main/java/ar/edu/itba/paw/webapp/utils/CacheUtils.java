package ar.edu.itba.paw.webapp.utils;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.util.Date;

public class CacheUtils {

    private static final int MAX_TIME = 31536000;


    public static Response sendConditionalCacheResponse(Request request, Object entity, EntityTag entityTag) {
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMustRevalidate(true);
        cacheControl.setNoTransform(true);
        Response.ResponseBuilder builder = request.evaluatePreconditions(entityTag);
        if (builder == null) {
            builder = Response.ok(entity);
            builder.tag(entityTag);
        }
        return builder.cacheControl(cacheControl).build();
    }

    public static Response sendUnconditionalCacheResponse(Object entity) {
        CacheControl cacheControl = CacheControl.valueOf("public, max-age=" + MAX_TIME + ", immutable");
        return Response.ok(entity).cacheControl(cacheControl).build();
    }
}
