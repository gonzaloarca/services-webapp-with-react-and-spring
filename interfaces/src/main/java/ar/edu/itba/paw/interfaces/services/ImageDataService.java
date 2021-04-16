package ar.edu.itba.paw.interfaces.services;

import com.sun.istack.internal.NotNull;

import javax.activation.DataSource;

public interface ImageDataService {

	String getImageType(@NotNull byte[] imageData);

	String getEncodedString(@NotNull byte[] imageData);

	DataSource getImageDataSource(@NotNull byte[] imageData);
}
