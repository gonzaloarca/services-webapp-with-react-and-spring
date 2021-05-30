package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "users_user_email_key", columnNames = {"user_email"})
})
public class UserWithImage extends UserAbstract{

    @AttributeOverrides({
            @AttributeOverride(name = "data", column = @Column(name = "user_image")),
            @AttributeOverride(name = "type", column = @Column(name = "image_type", length = 100))
    })
    private ByteImage byteImage;

    /*default*/UserWithImage(){}

    public UserWithImage(String email, String username, String phone, boolean isActive, boolean isVerified, ByteImage byteImage,
                LocalDateTime creationDate, String password) {
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.isActive = isActive;
        this.isVerified = isVerified;
        this.byteImage = byteImage;
        this.creationDate = creationDate;
        this.password = password;
    }

    public ByteImage getByteImage() {
        return byteImage;
    }

    public void setByteImage(ByteImage byteImage) {
        this.byteImage = byteImage;
    }

}
