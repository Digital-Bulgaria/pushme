package eu.balev.pushme.service;

import eu.balev.pushme.domain.User;

public interface ContainerService {
	
	public void canAccessContainer(User user, String containerId);
}
