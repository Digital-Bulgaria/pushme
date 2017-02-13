package eu.balev.pushme.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import eu.balev.pushme.domain.CurrentUser;
import eu.balev.pushme.repository.ContainerRepository;

@Controller
public class HomeController {
	
	@Autowired
	private ContainerRepository containerRepo;
	
	@RequestMapping("/")
	public ModelAndView root(@AuthenticationPrincipal CurrentUser currentUser) {
		return serveRoot(currentUser);
	}

	@RequestMapping("/home")
	public ModelAndView home(@AuthenticationPrincipal CurrentUser currentUser) {
		return serveRoot(currentUser);
	}

	private ModelAndView serveRoot(CurrentUser currentUser) {
		ModelAndView result = new ModelAndView();
		result.setViewName("index");
		return result;
	}
}
