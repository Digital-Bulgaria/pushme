package eu.balev.pushme.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.CurrentUser;
import eu.balev.pushme.repository.ContainerRepository;
import eu.balev.pushme.service.UserService;

@RestController
public class ContainerRestController {

	@Autowired
	private ContainerRepository containerRepo;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/api/containers/container", method = RequestMethod.POST)
	public @ResponseBody Container createContainer() {
		
		Container ctnr = new Container();
		
		Optional<CurrentUser> currentUserOpt = userService.getCurrentUser();
		currentUserOpt.ifPresent(currentUser -> ctnr.setUser(currentUser.getUser()));
		
		return containerRepo.save(ctnr);
	}
	
	
}
