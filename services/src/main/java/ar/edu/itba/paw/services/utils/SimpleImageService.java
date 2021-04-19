package ar.edu.itba.paw.services.utils;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.ByteImage;
import org.springframework.stereotype.Service;

@Service
public class SimpleImageService implements ImageService {

	@Override
	public ByteImage create(byte[] data, String type) {
		if(data == null || !isValidType(type))
			throw new RuntimeException("Error in image data or type");

		return new ByteImage(data, type);
	}

	// TODO: definir los tipos de imagen aceptados:
	// http://www.java2s.com/Code/Java/Network-Protocol/MapfileextensionstoMIMEtypesBasedontheApachemimetypesfile.htm
	@Override
	public boolean isValidType(String type) {
		if(type == null)
			return false;

		return type.equals("image/png")
				|| type.equals("image/jpg")
				|| type.equals("image/jpeg");
	}

	@Override
	public boolean isValidImage(ByteImage image) {
		return image != null &&
				image.getData() != null &&
				isValidType(image.getType());
	}
}
