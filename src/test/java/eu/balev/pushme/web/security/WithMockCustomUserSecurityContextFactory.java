package eu.balev.pushme.web.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import eu.balev.pushme.domain.CurrentUser;
import eu.balev.pushme.domain.User;

public class WithMockCustomUserSecurityContextFactory implements
		WithSecurityContextFactory<WithMockCustomUser> {
	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {

		User testUser = new User();
		testUser.setPasswordHash("password");
		testUser.setEmail(customUser.email());

		SecurityContext context = SecurityContextHolder.createEmptyContext();

		CurrentUser principal = new CurrentUser(testUser);

		Authentication auth = new UsernamePasswordAuthenticationToken(
				principal, "password", principal.getAuthorities());
		context.setAuthentication(auth);
		return context;
	}
}