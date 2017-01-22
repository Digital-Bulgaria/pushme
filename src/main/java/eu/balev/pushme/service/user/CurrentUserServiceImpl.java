package eu.balev.pushme.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.CurrentUser;
import eu.balev.pushme.domain.User;
import eu.balev.pushme.repository.ContainerRepository;

@Service("CurrentUserService")
public class CurrentUserServiceImpl implements CurrentUserService {

	private final Logger LOGGER = LoggerFactory.getLogger(CurrentUserServiceImpl.class);
	
	@Autowired
	private ContainerRepository containerRepo;

	@Override
	public boolean canAccessContainer(CurrentUser currentUser, String containerId) {
		
		//retrieve the container
		Container ctnr = containerRepo.findOne(containerId);
		if (ctnr == null)
		{
			LOGGER.debug("Access permissions for container that does not exist, we assume that everyone will be able to access it :-). "
					+ "ContainerID = {}", containerId);
			return true;
		}
		
		User owner = ctnr.getUser();
		User principal = currentUser != null ? currentUser.getUser() : null;
		
		//anonymous containers can be accessed by everyone
		if (owner == null)
		{
			LOGGER.debug("Requested permissions for container {}. This container is anonymous so we assume everyone can access it.", 
					containerId);
			return true;
		}
		
		//anonymous users can't access owned containers
		if (principal == null)
		{
			LOGGER.debug("Requested permissions for container {} but the container is not anonymous while the user is. Denying permission.", containerId);
			return false;
		}
		
		//check if the principal is an owner.
		boolean isOwner = principal.getId().equals(owner.getId());
		
		LOGGER.debug("Requested permissions for container {} by principal {}. Principle is owner == {} ", containerId, principal.getId(), isOwner);
		
		return isOwner;
	}

}
