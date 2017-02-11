package eu.balev.pushme.service.container;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.Request;
import eu.balev.pushme.domain.User;
import eu.balev.pushme.repository.ContainerRepository;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ContainerServiceImpl implements ContainerService {

	@Value("${pushme.max.anon.containers:#{10}}")
	private Integer maxNoAnonContainers;

	@Value("${pushme.max.requests.in.container:#{5}}")
	private Integer maxNoRequestsInContainer;

	@Autowired
	private ContainerRepository containerRepo;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContainerServiceImpl.class);

	private Object anonLock = new Object();

	@Override
	public String createContainer() {
		return createContainer(null);
	}

	@Override
	public String createContainer(User owner) {

		Container ctnr = new Container();

		if (owner != null) {
			LOGGER.debug("Creating a new container for authenticated user...");
			ctnr.setUser(owner);
		} else {
			LOGGER.debug("Creating a new container for an anonymous user...");

			synchronized (anonLock) {
				Long anonContainersCnt = containerRepo.countByUser(null);

				LOGGER.debug(
						"Currently there are {} anonymous containers... Maximum count which is allowed is {}.",
						anonContainersCnt, maxNoAnonContainers);

				pruneAnonymousContainers(anonContainersCnt.intValue(),
						maxNoAnonContainers);
			}
		}

		Container newCtnr = containerRepo.save(ctnr);

		return newCtnr.getId();
	}

	private void pruneAnonymousContainers(int totalCnts, int maxCnts) {
		if (totalCnts >= maxCnts) {
			int cntrsToPrune = totalCnts - maxCnts + 1;
			LOGGER.debug("We are going to prune {} anonymous containers.",
					cntrsToPrune);

			List<Container> containers = containerRepo
					.findOldestAnonymousContainers(new PageRequest(0,
							cntrsToPrune));

			containers.forEach(c -> {
				LOGGER.debug("Prune container with id {}.", c.getId());
				containerRepo.delete(c);
			});
		} else {
			LOGGER.debug(
					"There is no need to prune the anonymous containers since the maximum "
							+ "limit is not yet reached. Cuurent amount {}. Max {}.",
					totalCnts, maxCnts);
		}
	}

	@Override
	public void storeRequest(Container ctnr, Request request) {
		
		ctnr.addRequest(request);
		ctnr.pruneRequests(maxNoRequestsInContainer);
		
		containerRepo.save(ctnr);
	}
}
