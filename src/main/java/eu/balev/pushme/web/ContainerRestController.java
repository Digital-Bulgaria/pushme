package eu.balev.pushme.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.repository.ContainerRepository;

@RestController
public class ContainerRestController {

	@Autowired
	private ContainerRepository containerRepo;

	@RequestMapping(value = "/api/containers/container", method = RequestMethod.POST)
	public @ResponseBody Container createContainer() {
		return containerRepo.save(new Container());
	}
}
