package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "users_user_email_key", columnNames = {"user_email"})
})
public class User extends UserAbstract {

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
    }

    public User(UserWithImage userWithImage) {
        this.id = userWithImage.id;
        this.email = userWithImage.email;
        this.username = userWithImage.username;
        this.phone = userWithImage.phone;
        this.isActive = userWithImage.isActive;
        this.isVerified = userWithImage.isVerified;
        this.creationDate = userWithImage.creationDate;
    }
}
