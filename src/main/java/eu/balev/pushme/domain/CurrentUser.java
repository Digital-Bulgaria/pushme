package eu.balev.pushme.domain;

import java.util.Collections;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	
	private final User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPasswordHash(), Collections.emptyList());
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}