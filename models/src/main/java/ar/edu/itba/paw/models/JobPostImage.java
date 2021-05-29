package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "post_image")
public class JobPostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_image_image_id_seq")
    @SequenceGenerator(sequenceName = "post_image_image_id_seq", name = "post_image_image_id_seq", allocationSize = 1)
    @Column(name = "image_id", nullable = false)
    private long imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "post_image_post_id_fkey"))
    private JobPost jobPost;

    @AttributeOverrides({
            @AttributeOverride(name = "data", column = @Column(name = "image_data", nullable = false)),
            @AttributeOverride(name = "type", column = @Column(name = "image_type", length = 100, nullable = false))
    })
    private ByteImage byteImage;

    /*deafult*/JobPostImage() {

    }

    public JobPostImage(long imageId, JobPost jobPost, ByteImage byteImage) {
        this.imageId = imageId;
        this.jobPost = jobPost;
        this.byteImage = byteImage;
    }

    public JobPostImage(JobPost jobPost, ByteImage byteImage) {
        this.jobPost = jobPost;
        this.byteImage = byteImage;
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

    public ByteImage getByteImage() {
        return byteImage;
    }

    public void setByteImage(ByteImage byteImage) {
        this.byteImage = byteImage;
    }
}
