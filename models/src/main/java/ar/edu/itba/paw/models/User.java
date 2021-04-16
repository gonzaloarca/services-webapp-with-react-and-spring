package ar.edu.itba.paw.models;

import java.util.Objects;

public class User {
    private long id;
    private String email;
    private String username;
    private String userImage;
    private String phone;
    private boolean isActive;
    private boolean isVerified;

    public User() {
    }

    public User(String email, String username, String userImage, String phone) {
        this.email = email;
        this.username = username;
        this.userImage = userImage;
        this.phone = phone;
        this.isActive = true;
    }

    public User(long id, String email, String username, String userImage, String phone, boolean isActive) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.userImage = userImage;
        this.phone = phone;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", userImage='" + userImage + '\'' +
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
