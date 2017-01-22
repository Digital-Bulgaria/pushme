package eu.balev.pushme.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import eu.balev.pushme.service.user.UserService;

@Controller
public class UserRegistrationController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserRegistrationController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRegistrationFormValidator userRegFormValidator;
	
	@Autowired
    protected AuthenticationManager authenticationManager;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(userRegFormValidator);
	}

	@GetMapping(value = "/register")
	public String register(
			UserRegistrationForm userRegistrationForm) {

		LOGGER.debug("Serving a fresh registration page");

		return "register";
	}

	@PostMapping(value = "/register-create")
	public String createRule(@Valid UserRegistrationForm userRegistrationForm,
			BindingResult bindingResult, Model model,
			HttpServletRequest request) {

		if (bindingResult.hasErrors()) {
			return "register";
		}

		userService.createUser(userRegistrationForm);
		
		authenticateUserAndSetSession(userRegistrationForm.getUserEmail(), userRegistrationForm.getUserPassword(), request);
		
		return "index";
	}
	
	private void authenticateUserAndSetSession(String uName, String uPassword, HttpServletRequest request) {
		
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(uName, uPassword);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }
}
