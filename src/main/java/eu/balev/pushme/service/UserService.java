package eu.balev.pushme.service;

import eu.balev.pushme.domain.User;
import eu.balev.pushme.web.user.UserRegistrationForm;

public interface UserService {
	
	User createUser(UserRegistrationForm regForm);
}
