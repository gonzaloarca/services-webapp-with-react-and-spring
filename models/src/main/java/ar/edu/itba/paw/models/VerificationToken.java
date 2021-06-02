package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "verification_token")
public class VerificationToken implements Serializable {

	@Column(length = 100, nullable = false)
	private String token;

	@Id
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "verification_token_user_id_fkey"))
	private User user;

	@Column(nullable = false, name = "creation_date")
	private Instant creationDate;

	/* default */ VerificationToken() {
	}

	public VerificationToken(String token, User user, Instant creationDate) {
		this.token = token;
		this.user = user;
		this.creationDate = creationDate;
	}

	public String getToken() {
		return token;
	}

	public User getUser() {
		return user;
	}

	public Instant getCreationDate() {
		return creationDate;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setCreationDate(Instant creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		VerificationToken that = (VerificationToken) o;
		return token.equals(that.token);
	}

	@Override
	public int hashCode() {
		return Objects.hash(token);
	}
}
