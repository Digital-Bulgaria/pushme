package eu.balev.pushme.service.user;

import eu.balev.pushme.domain.CurrentUser;

/**
 * Describes some operations and checks related to the currently logged
 * principal.
 */
public interface CurrentUserService {

	/**
	 * Checks if the user can access the container with the given Id.
	 * 
	 * @param user
	 *            the currently logged in principal.
	 * @param containerId
	 *            the id of the container
	 * 
	 * @return <code>true</code> if the user can access the container with the
	 *         given id.
	 */
	public boolean canAccessContainer(CurrentUser user, String containerId);

	/**
	 * Checks if the user can access the rule with the given id.
	 * 
	 * @param currentUser
	 *            the currently logged in principal.
	 * @param ruleId
	 *            the id of the rule
	 * 
	 * @return <code>true</code> if the user can access the rule with the given
	 *         id.
	 */
	public boolean canAccessRule(CurrentUser currentUser, Long ruleId);
}
