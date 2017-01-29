package eu.balev.pushme.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eu.balev.pushme.repository.UserRepository;
import eu.balev.pushme.service.recaptcha.RecaptchaService;

@Component
public class UserRegistrationFormValidator implements Validator {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserRegistrationFormValidator.class);

	private final UserRepository userRepository;

	@Autowired
	private RecaptchaService recaptchService;

	@Autowired
	public UserRegistrationFormValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UserRegistrationForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {

		LOGGER.debug("Validating {}", target);
		UserRegistrationForm form = (UserRegistrationForm) target;
		validatePasswords(errors, form);
		validateEmail(errors, form);
		validateRecaptcha(errors, form);

	}

	private void validatePasswords(Errors errors, UserRegistrationForm form) {
		if (!form.getUserPassword().equals(form.getUserPasswordRepeat())) {
			errors.rejectValue("userPassword", "user.reg.password.no_match");
			errors.rejectValue("userPasswordRepeat",
					"user.reg.password.no_match");
		}
	}

	private void validateEmail(Errors errors, UserRegistrationForm form) {
		if (userRepository.findOneByEmail(form.getUserEmail()).isPresent()) {
			errors.rejectValue("userEmail", "user.reg.email.exists");
		}
	}

	private void validateRecaptcha(Errors errors, UserRegistrationForm form) {
		if (recaptchService.isServiceConfigured())
		{
			String recaptchaResponse = form.getRecaptchaResponse();
			if (recaptchaResponse == null 
					|| recaptchaResponse.isEmpty() ||
					!recaptchService.isResponseValid(null, recaptchaResponse))
			{
				errors.rejectValue("recaptchaResponse", "user.reg.captcha.not.solved");
			}
			
		}
	}
}