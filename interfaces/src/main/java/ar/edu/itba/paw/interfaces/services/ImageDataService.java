package ar.edu.itba.paw.interfaces.services;

import javax.activation.DataSource;

//TODO ver si este service está de más
public interface ImageDataService {

	String getImageType(byte[] imageData);

	String getEncodedString(byte[] imageData);

	DataSource getImageDataSource(byte[] imageData);
}
