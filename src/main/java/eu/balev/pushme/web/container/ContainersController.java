package eu.balev.pushme.web.container;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.CurrentUser;
import eu.balev.pushme.repository.ContainerRepository;

@Controller
public class ContainersController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContainersController.class);
	
	@Autowired
	private ContainerRepository containerRepo;
	
	@GetMapping(value = "/mycontainers")
	public ModelAndView createContainer(
			@AuthenticationPrincipal CurrentUser currentUser) {
		
		ModelAndView ret = new ModelAndView();
		
		if (currentUser == null)
		{
			LOGGER.debug("My containers is requested for logged out user. "
					+ "Perhaps the session has expired. Redirectring to the login page.");
			ret.setViewName("login");
		}
		else
		{
			List<Container> containers = containerRepo.findByUser(currentUser.getUser());
			
			LOGGER.debug("My containers is requested for logged in user with ID={}. Number of retrieved containers is {}.",
					currentUser.getUser().getId(),
					containers.size());

			ret.addObject("containers", containers);
			ret.setViewName("mycontainers");
		}

		
		return ret;
	}
}
