package ar.edu.itba.paw.webapp.utils;

import ar.edu.itba.paw.models.ByteImage;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class ImagesUtil {

    private static final int MAX_TIME = 31536000;

    public static ByteImage fromInputStream(FormDataBodyPart body) throws IOException {
        String mediaType = "image/" + body.getMediaType().getSubtype();
        if(!mediaType.equals("image/png") && !mediaType.equals("image/jpg") && !mediaType.equals("image/jpeg"))
            throw new IllegalArgumentException();
        byte[] data = IOUtils.toByteArray(body.getValueAs(InputStream.class));
        return new ByteImage(data,mediaType);
    }

    public static Response sendCacheableImageResponse(ByteImage byteImage, Request request){
        return CacheUtils.sendConditionalCacheResponse(request,new ByteArrayInputStream(byteImage.getData()));
    }
    public static Response sendUnconditionalCacheImageResponse(ByteImage byteImage){
        return CacheUtils.sendUnconditionalCacheResponse(new ByteArrayInputStream(byteImage.getData()));
    }
}
