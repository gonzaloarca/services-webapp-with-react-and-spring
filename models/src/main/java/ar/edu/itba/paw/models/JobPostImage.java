package ar.edu.itba.paw.models;

public class JobPostImage {

	private final long imageId;
	private final long postId;
	private final EncodedImage image;

	public JobPostImage(long imageId, long postId, EncodedImage image) {
		this.imageId = imageId;
		this.postId = postId;
		this.image = image;
	}

	public long getImageId() {
		return imageId;
	}

	public long getPostId() {
		return postId;
	}

	public EncodedImage getImage() {
		return image;
	}
}
