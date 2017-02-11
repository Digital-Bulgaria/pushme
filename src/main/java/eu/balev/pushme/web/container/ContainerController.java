package eu.balev.pushme.web.container;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.Request;
import eu.balev.pushme.domain.Rule;
import eu.balev.pushme.repository.ContainerRepository;
import eu.balev.pushme.service.container.ContainerService;
import eu.balev.pushme.service.request.RequestService;

@Controller
public class ContainerController {

	@Autowired
	private ContainerService containerService;
	
	@Autowired 
	private RequestService requestService;
	
	@Autowired
	private ContainerRepository containerRepo;

	@RequestMapping(value = "/container/{id}")
	public ResponseEntity<String> pushInContainer(@PathVariable("id") String id,
			HttpServletRequest httpRequest) {
		
		//get the container
		Container container = containerRepo.findOne(id);
		
		Request pushMeReq = requestService.mapRequest(httpRequest);
		
		containerService.storeRequest(container, pushMeReq);
		
		Rule rule = container.getBestRule(pushMeReq);
		
		ResponseEntity<String> response = new ResponseEntity<>(
				rule.getResponseBody(), 
				HttpStatus.valueOf(rule.getResponseCode()));
		
		return response;
	}

	@RequestMapping(value = "/container-inspect", method = RequestMethod.GET)
	public ModelAndView inspectContainer(
			@RequestParam(value = "id") String id) {

		return processContainer("inspectcontainer", id);
	}
	
	@RequestMapping(value = "/container-setup", method = RequestMethod.GET)
	public ModelAndView setupContainer(
			@RequestParam(value = "id") String id,
			@RequestParam(value = "newrule", required=false) String newrule) {

		ModelAndView ret = processContainer("setupcontainer", id);
		
		if (newrule != null)
		{
			ret.addObject("newrule", newrule);
		}
		
		return ret;
	}
	
	private ModelAndView processContainer(String viewName, 
			String ctnrID)
	{
		ModelAndView result = new ModelAndView();
		
		Container container = containerRepo.findOne(ctnrID);
		
		if (container == null)
		{
			result.setStatus(HttpStatus.NOT_FOUND);
			result.setViewName("containernotfound");
		}
		else
		{
			result.setViewName(viewName);
			result.addObject("container", container);
		}
		return result;
	}
}
