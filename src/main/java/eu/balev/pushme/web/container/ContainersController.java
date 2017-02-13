package eu.balev.pushme.web.container;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.CurrentUser;
import eu.balev.pushme.domain.User;
import eu.balev.pushme.repository.ContainerRepository;

@Controller
public class ContainersController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContainersController.class);

	@Autowired
	private ContainerRepository containerRepo;
	
	@Value("${pushme.max.reg.containers}")
	private Integer maxContainers;

	@GetMapping(value = "/mycontainers")
	public ModelAndView listContainers(
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
			List<Container> containers = containerRepo.findByUser(currentUser
					.getUser());

			LOGGER.debug(
					"My containers is requested for logged in user with ID={}. Number of retrieved containers is {}.",
					currentUser.getUser().getId(), containers.size());

			ret.addObject("containers", containers);
			ret.setViewName("mycontainers");
		}

		return ret;
	}
	
	@RequestMapping(value = "/mycontainers-status", method = RequestMethod.GET)
	public ModelAndView containerStatus(@AuthenticationPrincipal CurrentUser currentUser) {
		
		ModelAndView ret = new ModelAndView();
		
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
				
			ret.addObject("ctnrCnt", containerCnt);
			ret.addObject("maxReached", maxReached);
			
			ret.setViewName("containerstatlogged");
		}
		else
		{
			ret.setViewName("containerstatanon");
		}
		
		return ret;
	}
	
	@PreAuthorize("@CurrentUserService.canAccessContainer(principal, #containerId)")
	@PostMapping(value = "/mycontainers-delete")
	public String deleteContainer(@RequestParam("containerId") String containerId)
	{
		LOGGER.debug("Received delete request for container with id {}.", containerId);
		
		Container ctnr = containerRepo.findOne(containerId);
		if (ctnr == null)
		{
			LOGGER.debug("Unable to find container by id {}.", containerId);
		}
		else
		{
			containerRepo.delete(ctnr);
		}
		return "redirect:/mycontainers";
	}
}
