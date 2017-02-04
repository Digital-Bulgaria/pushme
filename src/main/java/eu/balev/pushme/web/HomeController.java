package eu.balev.pushme.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import eu.balev.pushme.domain.CurrentUser;
import eu.balev.pushme.domain.User;
import eu.balev.pushme.repository.ContainerRepository;

@Controller
public class HomeController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HomeController.class);
	
	@Autowired
	private ContainerRepository containerRepo;
	
	@Value("${pushme.max.reg.containers}")
	private Integer maxContainers;
	
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
		
		//count the number of containers
		if (currentUser != null)
		{
			User user = currentUser.getUser();
			Long containerCnt = containerRepo.countByUser(user);
			Boolean maxReached = containerCnt >= maxContainers;
			LOGGER.debug("User {} has {} containers. Maximum reached = {}. Serving the count to the home page.", 
					user.getId(), 
					containerCnt,
					maxReached);
			
			result.addObject("ctnrCnt", containerCnt);
			result.addObject("maxReached", maxReached);
			
		}
		
		result.setViewName("index");
		return result;
	}
}
