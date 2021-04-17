package ar.edu.itba.paw.services.utils;

import ar.edu.itba.paw.interfaces.services.ImageDataService;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ImageDataServiceImpl implements ImageDataService {

	@Override
	public String getImageType(byte[] imageData) {
		if(imageData == null)
			throw new RuntimeException("Parameter can't be null");

		InputStream is = new BufferedInputStream(new ByteArrayInputStream(imageData));
		String mimeType;
		try {
			mimeType = URLConnection.guessContentTypeFromStream(is);
		} catch (IOException e) {
			//TODO: decidir si es mejor otro tipo de excepción
			throw new RuntimeException(e.getMessage());
		}

		return mimeType;
	}

	@Override
	public String getEncodedString(byte[] imageData) {
		if(imageData == null)
			throw new RuntimeException("Parameter can't be null");

		byte[] encodedArray = Base64.getEncoder().encode(imageData);
		return new String(encodedArray, StandardCharsets.UTF_8);
	}

	@Override
	public DataSource getImageDataSource(byte[] imageData) {
		if(imageData == null)
			throw new RuntimeException("Parameter can't be null");

		String imageType = getImageType(imageData);
		return new ByteArrayDataSource(imageData, imageType);
	}
}
