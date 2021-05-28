package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "post_image")
public class JobPostImageUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_image_image_id_seq")
    @SequenceGenerator(sequenceName = "post_image_image_id_seq", name = "post_image_image_id_seq", allocationSize = 1)
    @Column(name = "image_id", nullable = false)
    private long imageId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "post_image_post_id_fkey"))
    private JobPost jobPost;

    @Column(name = "image_data", nullable = false)
    private byte[] imageBytes;

    @Column(name = "image_type", length = 100, nullable = false)
    private String imageType;

    /*deafult*/JobPostImageUrl() {

    }

    public JobPostImageUrl(long imageId, JobPost jobPost, byte[] imageBytes, String imageType) {
        this.imageId = imageId;
        this.jobPost = jobPost;
        this.imageBytes = imageBytes;
        this.imageType = imageType;
    }

    public JobPostImageUrl(JobPost jobPost, byte[] imageBytes, String imageType) {
        this.jobPost = jobPost;
        this.imageBytes = imageBytes;
        this.imageType = imageType;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
