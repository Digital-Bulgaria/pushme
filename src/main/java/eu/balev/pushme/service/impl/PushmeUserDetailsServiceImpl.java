package eu.balev.pushme.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import eu.balev.pushme.domain.CurrentUser;
import eu.balev.pushme.repository.UserRepository;

@Service
public class PushmeUserDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PushmeUserDetailsServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		Optional<eu.balev.pushme.domain.User> userOpt = userRepository
				.findOneByEmail(username);
		
		LOGGER.debug("Trying to load user {}. Success = {}.", username, userOpt.isPresent());

		return userOpt.map(CurrentUser::new).orElseThrow(
				() -> new UsernameNotFoundException("No user " + username));
	}
}
