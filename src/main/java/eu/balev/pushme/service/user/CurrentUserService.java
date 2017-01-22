package eu.balev.pushme.service.user;

import eu.balev.pushme.domain.CurrentUser;

public interface CurrentUserService {
	
	public void canAccessContainer(CurrentUser user, String containerId);
}
