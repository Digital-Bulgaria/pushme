package eu.balev.pushme.service.user;

import eu.balev.pushme.domain.User;
import eu.balev.pushme.web.user.UserRegistrationForm;

/**
 * Describes service methods for manipulating of users.
 */
public interface UserService {
	
	/**
	 * Creates a user based on the data submitted in the registration form.
	 * 
	 * @param regForm the registration form
	 * 
	 * @return the newly created user.
	 */
	User createUser(UserRegistrationForm regForm);
}
