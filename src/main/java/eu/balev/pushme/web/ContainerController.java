package eu.balev.pushme.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.Request;
import eu.balev.pushme.repository.ContainerRepository;
import eu.balev.pushme.repository.RequestRepository;

@Controller
public class ContainerController {

	// todo - check container existance
	// todo - clean up container

	@Autowired
	private ContainerRepository containerRepo;

	@Autowired
	private RequestRepository requestRepo;

	@RequestMapping(value = "/containers/{id}")
	public String pushInContainer(@PathVariable("id") String id,
			HttpServletRequest httpRequest) {

		Container container = containerRepo.findOne(id);

		RequestBuilder pushMeReqBuilder = new RequestBuilder(httpRequest);

		Request pushMeReq = pushMeReqBuilder.buildHeaders().build();

		pushMeReq.setContainer(container);

		requestRepo.save(pushMeReq);

		return "response";
	}

	@RequestMapping(value = "/containers/inspect", method = RequestMethod.GET)
	public ModelAndView inspectContainer(
			@RequestParam(value = "id", required = false) String id) {

		Container container = containerRepo.findOne(id);
		
		ModelAndView result = new ModelAndView();

		result.setViewName("inspectcontainer");
		result.addObject("container", container);

		return result;
	}
}
