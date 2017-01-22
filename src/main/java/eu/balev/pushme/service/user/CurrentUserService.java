package eu.balev.pushme.service.user;

import eu.balev.pushme.domain.CurrentUser;

public interface CurrentUserService {
	
	public boolean canAccessContainer(CurrentUser user, String containerId);
}
