package ar.edu.itba.paw.webapp.utils;

import ar.edu.itba.paw.models.ByteImage;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class ImagesUtil {

    public static ByteImage fromInputStream(FormDataBodyPart body) throws IOException {
        String mediaType = "image/" + body.getMediaType().getSubtype();
        if(!mediaType.equals("image/png") && !mediaType.equals("image/jpg") && !mediaType.equals("image/jpeg"))
            throw new IllegalArgumentException();
        byte[] data = IOUtils.toByteArray(body.getValueAs(InputStream.class));
        return new ByteImage(data,mediaType);
    }

    public static Response sendCachableImageResponse(ByteImage byteImage,Request request){
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(86400);
        EntityTag etag = new EntityTag(Integer.toString(byteImage.hashCode()));
        Response.ResponseBuilder builder = request.evaluatePreconditions(etag);
        if(builder == null){
            builder = Response.ok(new ByteArrayInputStream(byteImage.getData()));
            builder.tag(etag);
        }
        return builder.cacheControl(cacheControl).build();
    }
}
