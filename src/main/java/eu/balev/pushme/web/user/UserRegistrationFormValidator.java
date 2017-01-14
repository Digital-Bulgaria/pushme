package eu.balev.pushme.web.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eu.balev.pushme.repository.UserRepository;

@Component
public class UserRegistrationFormValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationFormValidator.class);
    
    private final UserRepository userRepository;

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
        
    }

    private void validatePasswords(Errors errors, UserRegistrationForm form) {
        if (!form.getUserPassword().equals(form.getUserPasswordRepeat())) {
            errors.reject("password.no_match", "Passwords do not match");
        }
    }

    private void validateEmail(Errors errors, UserRegistrationForm form) {
        if (userRepository.findOneByEmail(form.getUserEmail()).isPresent()) {
            errors.reject("email.exists", "User with this email already exists");
        }
    }
}