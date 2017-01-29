package eu.balev.pushme.service.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.User;
import eu.balev.pushme.repository.ContainerRepository;

@Service
public class ContainerServiceImpl implements ContainerService{

	@Autowired
	private ContainerRepository containerRepo;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContainerServiceImpl.class);
	
	@Override
	public String createContainer() {
		return createContainer(null);
	}

	@Override
	public String createContainer(User owner) {
		
		Container ctnr = new Container();
		
		if (owner != null)
		{
			LOGGER.debug("Creating a new container for authenticated user...");
			ctnr.setUser(owner);
		}
		else
		{
			LOGGER.debug("Creating a new container for an anonymous user...");
			Long anonContainersCnt = containerRepo.countByUser(null);
			LOGGER.debug("Currently there are {} anonymous containers...", anonContainersCnt);
		}
		
		Container newCtnr = containerRepo.save(ctnr);
		
		return newCtnr.getId();
	}
}
