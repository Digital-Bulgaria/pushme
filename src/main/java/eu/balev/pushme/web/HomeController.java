package eu.balev.pushme.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import eu.balev.pushme.domain.CurrentUser;
import eu.balev.pushme.domain.User;
import eu.balev.pushme.repository.ContainerRepository;

@Controller
public class HomeController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HomeController.class);

	@Value("${pushme.max.reg.containers}")
	private Integer maxContainers;

	@Autowired
	private ContainerRepository containerRepo;

	@RequestMapping("/")
	public String root() {
		return "index";
	}

	@RequestMapping("/home")
	public String home() {
		return "index";
	}
	
	@RequestMapping(value = "/mycontainers-status", method = RequestMethod.GET)
	public String containerStatus(@AuthenticationPrincipal CurrentUser currentUser) {
		return (currentUser != null) ? 
				"containerstatlogged" : 
					"containerstatanon";
	}
	
	@ModelAttribute
	private void fillCtnrStatus(
			@AuthenticationPrincipal CurrentUser currentUser, 
			ModelMap modelMap) {
		// count the number of containers
		if (currentUser != null) {
			User user = currentUser.getUser();
			Long containerCnt = containerRepo.countByUser(user);
			Boolean maxReached = containerCnt >= maxContainers;
			LOGGER.debug(
					"User {} has {} containers. Maximum possible are {}. Maximum reached = {}. Serving the condition to the home page.",
					user.getId(), 
					containerCnt, 
					maxContainers,
					maxReached);

			modelMap.addAttribute("maxReached", maxReached);
			modelMap.addAttribute("ctnrCnt", containerCnt);
		}

	}
}
