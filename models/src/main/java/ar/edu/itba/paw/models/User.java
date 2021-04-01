package ar.edu.itba.paw.models;

public class User {
    private long id;
    private String email;
    private String username;
    private String userImage;
    private String phone;
    private boolean isProfessional;
    private boolean isActive;

    public User() {
    }

    public User(String email, String username, String userImage, String phone, boolean isProfessional) {
        this.email = email;
        this.username = username;
        this.userImage = userImage;
        this.phone = phone;
        this.isProfessional = isProfessional;
        this.isActive=true;
    }

    public User(long id, String email, String username, String userImage, String phone, boolean isProfessional, boolean isActive) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.userImage = userImage;
        this.phone = phone;
        this.isProfessional = isProfessional;
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

    public boolean isProfessional() {
        return isProfessional;
    }

    public void setProfessional(boolean professional) {
        isProfessional = professional;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return email;
    }
}
