package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "users_user_email_key", columnNames = {"user_email"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_user_id_seq")
    @SequenceGenerator(sequenceName = "users_user_id_seq", name = "users_user_id_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    private long id;

    @Column(length = 100, nullable = false, unique = true, name = "user_email")
    private String email;

    @Column(length = 100, nullable = false, name = "user_name")
    private String username;

    @Column(length = 100, nullable = false, name = "user_phone")
    private String phone;

    @Column(nullable = false, name = "user_is_active")
    private boolean isActive;

    @Column(nullable = false, name = "user_is_verified")
    private boolean isVerified;

    @AttributeOverrides({
            @AttributeOverride(name = "data", column = @Column(name = "user_image")),
            @AttributeOverride(name = "type", column = @Column(name = "image_type", length = 100))
    })
    private ByteImage byteImage = new ByteImage(null, null);

    @Transient
    private EncodedImage image; //Se setea en el DAO

    @Column(name = "user_creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(length = 100, nullable = false, name = "user_password")
    private String password;    //no tiene getter para evitar sea utilizada en cualquier lado

    /* default */ User() {
    }

    public User(long id, String email, String username, String phone, boolean isActive, boolean isVerified, LocalDateTime creationDate) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.isActive = isActive;
        this.isVerified = isVerified;
        this.creationDate = creationDate;
        this.image = new EncodedImage(null, null);
        this.byteImage = new ByteImage(null, null);
    }

    public User(String email, String username, String phone, boolean isActive, boolean isVerified, ByteImage byteImage,
                EncodedImage encodedImage, LocalDateTime creationDate, String password) {
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.isActive = isActive;
        this.isVerified = isVerified;
        this.byteImage = byteImage;
        this.creationDate = creationDate;
        this.password = password;
        this.image = encodedImage;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public EncodedImage getImage() {
        return image;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public ByteImage getByteImage() {
        return byteImage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public void setByteImage(ByteImage byteImage) {
        this.byteImage = byteImage;
    }

    public void setImage(EncodedImage image) {
        this.image = image;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
