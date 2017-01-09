package eu.balev.pushme.service.impl;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class PushmeUserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		// TODO:
		// currently hardcoded:

		if ("lucho@push.me".equals(username)) {
			return new User(username, "dummy", Collections.emptyList());
		} 
		else
			throw new UsernameNotFoundException(
					"The required user was not found!");
	}

}
