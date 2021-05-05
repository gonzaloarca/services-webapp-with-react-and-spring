package ar.edu.itba.paw.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {
    private final long id;
    private final String email;
    private final String username;
    private final String phone;
    private final boolean isActive;
    private final boolean isVerified;
    private final EncodedImage image;
    private final LocalDateTime creationDate;

    public User(long id, String email, String username, String phone, boolean isActive, boolean isVerified, EncodedImage image, LocalDateTime creationDate) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.isActive = isActive;
        this.isVerified = isVerified;
        this.image = image;
        this.creationDate = creationDate;
    }

    public User(long id, String email, String username, String phone, boolean isActive, boolean isVerified, LocalDateTime creationDate) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.isActive = isActive;
        this.isVerified = isVerified;
        this.creationDate = creationDate;
        this.image = null;
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
