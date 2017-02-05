package eu.balev.pushme.service.user;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.CurrentUser;
import eu.balev.pushme.domain.Rule;
import eu.balev.pushme.domain.User;
import eu.balev.pushme.repository.ContainerRepository;
import eu.balev.pushme.repository.RuleRepository;

@Service("CurrentUserService")
public class CurrentUserServiceImpl implements CurrentUserService {

	private final Logger LOGGER = LoggerFactory
			.getLogger(CurrentUserServiceImpl.class);

	@Autowired
	private ContainerRepository containerRepo;

	@Autowired
	private RuleRepository ruleRepo;

	@Override
	public boolean canAccessContainer(CurrentUser currentUser,
			String containerId) {

		// retrieve the container
		Container ctnr = containerRepo.findOne(containerId);
		if (ctnr == null) {
			LOGGER.debug(
					"Access permissions for container that does not exist are requested, "
							+ "we assume that everyone will be able to access it :-). "
							+ "ContainerID = {}", containerId);
			return true;
		}

		return canAccessContainer(currentUser, ctnr);
	}

	private boolean canAccessContainer(CurrentUser currentUser, Container ctnr) {
		Objects.requireNonNull(ctnr);

		User owner = ctnr.getUser();
		User principal = currentUser != null ? currentUser.getUser() : null;

		// anonymous containers can be accessed by everyone
		if (owner == null) {
			LOGGER.debug(
					"Requested permissions for container {}. "
							+ "This container is anonymous so we assume everyone can access it.",
					ctnr.getId());
			return true;
		}

		// anonymous users can't access owned containers
		if (principal == null) {
			LOGGER.debug(
					"Requested permissions for container {} but the container is not "
							+ "anonymous while the user is. Denying permission.",
					ctnr.getId());
			return false;
		}

		// check if the principal is an owner.
		boolean isOwner = principal.getId().equals(owner.getId());

		LOGGER.debug("Requested permissions for container {} by principal {}. "
				+ "Principal is owner == {} ", ctnr.getId(), principal.getId(),
				isOwner);

		return isOwner;
	}

	@Override
	public boolean canAccessRule(CurrentUser currentUser, Long ruleId) {
		Rule rule = ruleRepo.findOne(ruleId);
		if (rule == null) {
			LOGGER.debug(
					"Access permissions for rule that does not exist are requested, we assume that everyone will be able to access it :-). "
							+ "RuleId = {}", ruleId);
			return true;
		}

		Container ctnr = rule.getContainer();
		if (ctnr == null) {
			LOGGER.debug(
					"Access permissions for orphaned rule are requested... This is strange. We assume that everyone can access it."
							+ "RuleId = {}", ruleId);
		}

		return canAccessContainer(currentUser, ctnr);
	}
	
	@Override
	public boolean canAccessRule(String principal, Long ruleId) {
		return canAccessRule((CurrentUser)null, ruleId);
	}
}
