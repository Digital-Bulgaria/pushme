package eu.balev.pushme.web.container;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import eu.balev.pushme.domain.CurrentUser;
import eu.balev.pushme.domain.User;
import eu.balev.pushme.service.container.ContainerService;

@RestController
public class ContainerRestController {

	@Autowired
	private ContainerService containerService;
	
	@RequestMapping(value = "/api/container/container", method = RequestMethod.POST)
	public @ResponseBody ContainerDTO createContainer(@AuthenticationPrincipal CurrentUser currentUser) {
		
		Optional<User> ownerOpt = Optional.ofNullable(currentUser).map(CurrentUser::getUser);
		
		String ctnrId;
		if (!ownerOpt.isPresent())
		{
			ctnrId= containerService.createContainer();
		}
		else
		{
			ctnrId= containerService.createContainer(ownerOpt.get());
		}
		
		return new ContainerDTO(ctnrId);
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
