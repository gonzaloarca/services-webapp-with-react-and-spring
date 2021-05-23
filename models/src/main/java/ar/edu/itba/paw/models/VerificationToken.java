package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

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
}
