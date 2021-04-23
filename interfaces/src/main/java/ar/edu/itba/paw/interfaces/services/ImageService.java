package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.ByteImage;

public interface ImageService {

	ByteImage create(byte[] data, String type);

	boolean isValidType(String type);

	boolean isValidImage(ByteImage image);

}
