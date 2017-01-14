package eu.balev.pushme.web.user;

import javax.validation.constraints.NotNull;

public class UserRegistrationForm {
	
	@NotNull
	private String userEmail;
	
	@NotNull
	private String userPassword;
	
	@NotNull
	private String userPasswordRepeat;

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
	
	
}
