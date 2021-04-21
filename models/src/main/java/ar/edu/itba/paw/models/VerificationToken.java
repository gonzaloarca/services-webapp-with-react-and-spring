package ar.edu.itba.paw.models;

import java.time.Instant;

public class VerificationToken {
	private final String token;
	private final User user;
	private final Instant creationDate;

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
