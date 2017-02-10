package eu.balev.pushme.service.container;

import eu.balev.pushme.domain.User;

/**
 * The service takes care about some container related operations.
 */
public interface ContainerService {

	/**
	 * Creates anonymous container.
	 * 
	 * @return the id of the new container.
	 */
	String createContainer();

	/**
	 * Create a container with a given owner. If the owner had not been
	 * specified, then the container will be anonymous.
	 * 
	 * @param owner the owner of the container.
	 * 
	 * @return the id of the newly created container.
	 */
	String createContainer(User owner);

}