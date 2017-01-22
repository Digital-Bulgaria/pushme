package eu.balev.pushme.web.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.CurrentUser;
import eu.balev.pushme.repository.ContainerRepository;

@RestController
public class ContainerRestController {

	@Autowired
	private ContainerRepository containerRepo;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContainerRestController.class);
	
	@RequestMapping(value = "/api/container/container", method = RequestMethod.POST)
	public @ResponseBody ContainerDTO createContainer(@AuthenticationPrincipal CurrentUser currentUser) {
		
		Container ctnr = new Container();
		
		if (currentUser != null)
		{
			LOGGER.debug("Creating a new container for authenticated user...");
			ctnr.setUser(currentUser.getUser());
		}
		else
		{
			LOGGER.debug("Creating a new container for an anonymous user...");
		}
		
		Container newCtnr = containerRepo.save(ctnr);
		
		return new ContainerDTO(newCtnr.getId());
	}
	
	static class ContainerDTO
	{
		private final String id;
		
		ContainerDTO(String id)
		{
			this.id = id;
		}
		public String getId() {
			return id;
		}
	}
	
}
