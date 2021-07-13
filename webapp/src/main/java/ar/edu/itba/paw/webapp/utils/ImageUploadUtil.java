package ar.edu.itba.paw.webapp.utils;

import ar.edu.itba.paw.models.ByteImage;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

import static java.net.URLConnection.guessContentTypeFromStream;

public class ImageUploadUtil {

    public static ByteImage fromInputStream(InputStream file) throws IOException {
        String type = URLConnection.guessContentTypeFromStream(file);
        if(!type.equals("png") && !type.equals("jpg"))
            throw new IllegalArgumentException();
        byte[] data = IOUtils.toByteArray(file);
        return new ByteImage(data,type);
    }
}
