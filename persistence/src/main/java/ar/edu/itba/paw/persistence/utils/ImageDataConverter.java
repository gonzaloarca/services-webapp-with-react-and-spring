package ar.edu.itba.paw.persistence.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

//Clase con métodos estáticos para manejar la conversión de imagenes
public class ImageDataConverter {

	public static String getImageType(byte[] imageData) {
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

	public static String getEncodedString(byte[] imageData) {
		if(imageData == null)
			throw new RuntimeException("Parameter can't be null");

		byte[] encodedArray = Base64.getEncoder().encode(imageData);
		return new String(encodedArray, StandardCharsets.UTF_8);
	}
}
