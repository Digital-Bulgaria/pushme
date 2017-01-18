package eu.balev.pushme.service;

import java.util.Optional;

import eu.balev.pushme.domain.CurrentUser;
import eu.balev.pushme.domain.User;
import eu.balev.pushme.web.user.UserRegistrationForm;

public interface UserService {
	
	User createUser(UserRegistrationForm regForm);
	
	Optional<CurrentUser> getCurrentUser();
}
