package ar.edu.itba.paw.interfaces.services;

import javax.activation.DataSource;

public interface ImageDataService {

	String getImageType(byte[] imageData);

	String getEncodedString(byte[] imageData);

	DataSource getImageDataSource(byte[] imageData);
}
