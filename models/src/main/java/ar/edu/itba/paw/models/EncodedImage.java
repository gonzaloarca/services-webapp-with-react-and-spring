package ar.edu.itba.paw.models;

//Clase wrapper para las imagenes codificadas en Base64 para mostrar y su tipo
public class EncodedImage {
	private final String string;
	private final String type;

	public EncodedImage(String encodedString, String type) {
		this.string = encodedString;
		this.type = type;
	}

	public String getString() {
		return string;
	}

	public String getType() {
		return type;
	}
}
