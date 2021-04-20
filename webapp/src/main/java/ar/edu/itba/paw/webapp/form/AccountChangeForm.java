package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validation.ValidImage;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AccountChangeForm {

	@NotBlank
	@Size(max = 100)
	private String name;

	@Pattern(regexp = "^\\+?[0-9- ]{7,50}")
	private String phone;

	@ValidImage
	private MultipartFile avatar;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public MultipartFile getAvatar() {
		return avatar;
	}

	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}
}
