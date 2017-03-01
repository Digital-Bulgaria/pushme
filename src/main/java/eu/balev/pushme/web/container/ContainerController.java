package eu.balev.pushme.web.container;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.Request;
import eu.balev.pushme.domain.Rule;
import eu.balev.pushme.repository.ContainerRepository;
import eu.balev.pushme.service.container.ContainerService;
import eu.balev.pushme.service.request.RequestService;
import eu.balev.pushme.web.error.ObjectNotFoundException;

@Controller
public class ContainerController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContainerController.class);

	@Autowired
	private ContainerService containerService;

	@Autowired
	private RequestService requestService;

	@Autowired
	private ContainerRepository containerRepo;

	/**
	 * Handles a push request for the controller.
	 * 
	 * @param id
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/container/{id}")
	public ResponseEntity<String> pushInContainer(
			@PathVariable("id") String id, HttpServletRequest httpRequest) {

		// get the container
		Container container = containerRepo.findOne(id);

		Request pushMeReq = requestService.mapRequest(httpRequest);

		containerService.storeRequest(container, pushMeReq);

		Rule rule = container.getBestRule(pushMeReq);

		ResponseEntity<String> response = new ResponseEntity<>(
				rule.getResponseBody(), HttpStatus.valueOf(rule
						.getResponseCode()));

		return response;
	}

	/**
	 * Inspects a the contents of a container.
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/container-inspect", method = RequestMethod.GET)
	public ModelAndView inspectContainer(@RequestParam(value = "id") String id) {

		return processContainer("inspectcontainer", id);
	}

	/**
	 * Sets up a container
	 * 
	 * @param id
	 * @param newrule
	 * @return
	 */
	@RequestMapping(value = "/container-setup", method = RequestMethod.GET)
	public ModelAndView setupContainer(@RequestParam(value = "id") String id,
			@RequestParam(value = "newrule", required = false) String newrule) {

		ModelAndView ret = processContainer("setupcontainer", id);

		if (newrule != null) {
			ret.addObject("newrule", newrule);
		}

		return ret;
	}

	@PostMapping(value = "/container-rename")
	public String renameContainer(
			@RequestParam("containerId") String containerId,
			@RequestParam(name = "newName", required = false) String newName,
			RedirectAttributes redirectAttributes) {

		LOGGER.debug("Received rename request for container with id {}.",
				containerId);

		Container ctnr = containerRepo.findOne(containerId);
		if (ctnr == null) {
			throw new ObjectNotFoundException();
		} else if (newName == null || newName.isEmpty()) {
			LOGGER.debug(
					"Rename for container with id {} was requested but no new name was provided.",
					containerId);
		} else {
			LOGGER.debug("Renaming container with id {}.", containerId);
			ctnr.setName(newName);
			containerRepo.save(ctnr);

			redirectAttributes.addAttribute("id", containerId);
		}
		return "redirect:/container-setup";
	}

	private ModelAndView processContainer(String viewName, String ctnrID) {

		ModelAndView result = new ModelAndView();

		Container container = Optional
				.ofNullable(containerRepo.findOne(ctnrID)).orElseThrow(
						() -> new ObjectNotFoundException());
		
		result.setViewName(viewName);
		result.addObject("container", container);
		
		return result;
	}
}
