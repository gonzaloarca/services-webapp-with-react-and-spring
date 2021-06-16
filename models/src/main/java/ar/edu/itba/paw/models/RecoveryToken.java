package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "recovery_token")
public class RecoveryToken implements Serializable {

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "verification_token_user_id_fkey"))
    private User user;

    @Column(length = 100, nullable = false)
    private String token;

    @Column(nullable = false, name = "creation_date")
    private Instant creationDate;

    /*default*/RecoveryToken() {

    }

    public RecoveryToken(User user, String token, Instant creationDate) {
        this.user = user;
        this.token = token;
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecoveryToken that = (RecoveryToken) o;
        return token.equals(that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
