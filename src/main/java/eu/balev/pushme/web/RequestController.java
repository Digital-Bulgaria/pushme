package eu.balev.pushme.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.balev.pushme.domain.Request;
import eu.balev.pushme.repository.RequestRepository;

@Controller
public class RequestController {

	@Autowired
	private RequestRepository requestRepo;
	
	@RequestMapping(value = "/requests-details", method = RequestMethod.GET)
	public ModelAndView inspectContainer(
			@RequestParam(value = "containerid") String containerId,
			@RequestParam(value = "requestid") long requestId) {

		Request request = requestRepo.findOne(requestId);
		
		ModelAndView result = new ModelAndView();

		result.setViewName("request");
		result.addObject("request", request);

		return result;
	}
	
}
