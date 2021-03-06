package eu.balev.pushme.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import eu.balev.pushme.domain.User;
import eu.balev.pushme.repository.UserRepository;
import eu.balev.pushme.web.user.UserRegistrationForm;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	@Override
	public User createUser(UserRegistrationForm userRegForm) {

		// prepare the user
		User user = new User();

		// set fields
		user.setEmail(userRegForm.getUserEmail());
		user.setPasswordHash(new BCryptPasswordEncoder().encode(userRegForm
				.getUserPassword()));

		// store the user
		return userRepo.save(user);
	}
}
