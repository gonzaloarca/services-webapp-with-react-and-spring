package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public class UserAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_user_id_seq")
    @SequenceGenerator(sequenceName = "users_user_id_seq", name = "users_user_id_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    protected long id;

    @Column(length = 100, nullable = false, unique = true, name = "user_email")
    protected String email;

    @Column(length = 100, nullable = false, name = "user_name")
    protected String username;

    @Column(length = 100, nullable = false, name = "user_phone")
    protected String phone;

    @Column(nullable = false, name = "user_is_active")
    protected boolean isActive;

    @Column(nullable = false, name = "user_is_verified")
    protected boolean isVerified;

    @Column(name = "user_creation_date", nullable = false)
    protected LocalDateTime creationDate;

    @Column(length = 100, nullable = false, name = "user_password")
    protected String password;    //no tiene getter para evitar sea utilizada en cualquier lado

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

    public LocalDateTime getCreationDate() {
        return creationDate;
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
        //fixme está bien este equals?
        if (this == o) return true;
        if (o == null || !getClass().isAssignableFrom(o.getClass())) return false;
        UserAbstract user = (UserAbstract) o;
        return id == user.id && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
