package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validation.DistinctFields;
import ar.edu.itba.paw.webapp.validation.MatchingFields;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@MatchingFields(
		field = "newPass",
		fieldMatch = "repeatNewPass",
		message = "Passwords do not match"
)
@DistinctFields(
		field = "currentPass",
		distinctField = "newPass",
		message = "New password cannot be the same as the old one"
)
public class PasswordChangeForm {

	@Size(min = 8, max = 100)
	private String currentPass;

	@Size(min = 8, max = 100)
	private String newPass;

	@NotBlank
	private String repeatNewPass;

	public String getCurrentPass() {
		return currentPass;
	}

	public void setCurrentPass(String currentPass) {
		this.currentPass = currentPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getRepeatNewPass() {
		return repeatNewPass;
	}

	public void setRepeatNewPass(String repeatNewPass) {
		this.repeatNewPass = repeatNewPass;
	}
}
