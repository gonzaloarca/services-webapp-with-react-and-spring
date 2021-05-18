package ar.edu.itba.paw.models;

import javax.persistence.Embeddable;
import java.util.Arrays;
import java.util.Objects;

//Esta clase es un wrapper para las imagenes guardadas en forma de array de bytes y su content type
@Embeddable
public class ByteImage {
	private final byte[] data;
	private final String type;

	public ByteImage(byte[] data, String type) {
		this.data = data;
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public String getType() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ByteImage byteImage = (ByteImage) o;
		return Arrays.equals(data, byteImage.data) && type.equals(byteImage.type);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(type);
		result = 31 * result + Arrays.hashCode(data);
		return result;
	}
}
