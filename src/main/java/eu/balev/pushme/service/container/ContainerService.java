package eu.balev.pushme.service.container;

import eu.balev.pushme.domain.User;

public interface ContainerService {

	String createContainer();
	
	String createContainer(User owner);	
}