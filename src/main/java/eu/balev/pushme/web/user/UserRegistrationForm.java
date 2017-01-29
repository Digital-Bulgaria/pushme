package eu.balev.pushme.web.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class UserRegistrationForm {

	@Email(message = "{user.reg.invalid.email}")
	@NotNull(message = "{user.reg.no.email}")
	@Size(min = 1, message = "{user.reg.no.email}")
	private String userEmail;

	@NotNull(message = "{user.reg.no.pass}")
	@Size(min = 1, message = "{user.reg.no.pass}")
	private String userPassword;

	@NotNull(message = "{user.reg.no.pass}")
	@Size(min = 1, message = "{user.reg.no.pass}")
	private String userPasswordRepeat;
	
	private String recaptchaResponse;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPasswordRepeat() {
		return userPasswordRepeat;
	}

	public void setUserPasswordRepeat(String userPasswordRepeat) {
		this.userPasswordRepeat = userPasswordRepeat;
	}

	public String getRecaptchaResponse() {
		return recaptchaResponse;
	}

	public void setRecaptchaResponse(String recaptchaResponse) {
		this.recaptchaResponse = recaptchaResponse;
	}
}
