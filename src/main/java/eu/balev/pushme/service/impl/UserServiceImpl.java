package eu.balev.pushme.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.balev.pushme.domain.User;
import eu.balev.pushme.repository.UserRepository;
import eu.balev.pushme.service.UserService;
import eu.balev.pushme.web.user.UserRegistrationForm;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;
	
	@Override
	public void registerUser(UserRegistrationForm userRegForm) {
		// TODO Auto-generated method stub
		User user = new User();
		
		user.setEmail(userRegForm.getUserEmail());
		//todo
		userRepo.save(user);
	}

}
