package ar.edu.itba.paw.models;

import java.util.Objects;

public class User {
    private final long id;
    private final String email;
    private final String username;
    private final String userImage;
    private final String phone;
    private final boolean isActive;
    private final boolean isVerified;

    public User(long id,String email, String username, String userImage, String phone,boolean isVerified) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.userImage = userImage;
        this.phone = phone;
        this.isActive = true;
        this.isVerified=isVerified;
    }

    public User(long id, String email, String username, String userImage, String phone,boolean isVerified, boolean isActive) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.userImage = userImage;
        this.phone = phone;
        this.isActive = isActive;
        this.isVerified = isVerified;
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

    public String getUserImage() {
        return userImage;
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
