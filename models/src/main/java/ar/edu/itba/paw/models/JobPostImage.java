package ar.edu.itba.paw.models;

public class JobPostImage {

	private final long imageId;
	private final long postId;
	//private final byte[] imageData;			//Este campo parece estar de más
	private final String encodedData;
	private final String imageType;

	public JobPostImage(long imageId, long postId, String encodedData, String imageType) {
		this.imageId = imageId;
		this.postId = postId;
		this.encodedData = encodedData;
		this.imageType = imageType;
	}

	public long getImageId() {
		return imageId;
	}

	public long getPostId() {
		return postId;
	}

	public String getEncodedData() {
		return encodedData;
	}

	public String getImageType() {
		return imageType;
	}
}
