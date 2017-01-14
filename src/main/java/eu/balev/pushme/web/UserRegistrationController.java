package eu.balev.pushme.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserRegistrationController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserRegistrationController.class);

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(
			@RequestParam(name = "error", required = false) String error) {

		LOGGER.debug("Serving a registration page, error={}", error);

		ModelAndView ret = new ModelAndView();

		ret.setViewName("register");
		ret.addObject("error", error);

		return ret;
	}

	@PostMapping(value = "/register-create")
	public String createRule(@Valid UserRegistrationForm regForm,
			BindingResult bindingResult, 
			Model model) {

		if (bindingResult.hasErrors()) {
			return "register";
		}

		return "home";
	}
}
