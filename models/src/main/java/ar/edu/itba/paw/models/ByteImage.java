package ar.edu.itba.paw.models;

//Esta clase es un wrapper para las imagenes guardadas en forma de array de bytes y su content type
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
}
