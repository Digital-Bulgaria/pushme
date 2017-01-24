package eu.balev.pushme.web;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.Rule;
import eu.balev.pushme.repository.RuleRepository;
import eu.balev.pushme.service.rule.RuleService;
import eu.balev.pushme.web.container.ContainersController;

@Controller
public class RulesController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RulesController.class);
	
	@Autowired
	private RuleService ruleService;
	
	@Autowired
	private RuleRepository ruleRepo;

	private static final List<String> allRequestMethod;

	private static final List<Integer> allResponseCodes;

	static {
		allRequestMethod = new LinkedList<>();
		allRequestMethod.add("ALL");
		for (RequestMethod method : RequestMethod.values()) {
			allRequestMethod.add(method.toString());
		}

		allResponseCodes = new LinkedList<>();
		Stream.of(HttpStatus.values()).forEach(
				s -> allResponseCodes.add(s.value()));
	}

	@ModelAttribute("allMethods")
	private List<String> getAllMethods() {
		return Collections.unmodifiableList(allRequestMethod);
	}

	@ModelAttribute("allResponseCodes")
	private List<Integer> getAllResponseCodes() {
		return Collections.unmodifiableList(allResponseCodes);
	}

	@RequestMapping(value = "/rules-new")
	public String newRule(RuleForm ruleForm,
			@RequestParam(value = "containerid") String containerId) {

		ruleForm.setContainerId(containerId);

		return "rule";
	}

	@PostMapping(value = "/rules-create")
	public String createRule(@Valid RuleForm ruleForm,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "rule";
		}
		
		ruleService.createOrUpdate(ruleForm);
		
		model.addAttribute("containerid", ruleForm.getContainerId());
		
		return "rulecreated";
	}
	
	//TODO: Check permissions
	//@PreAuthorize("@CurrentUserService.canAccessContainer(principal, #containerId)")
	@PostMapping(value = "/rules-delete")
	public String deleteContainer(@RequestParam("ruleId") Long ruleId,
			RedirectAttributes redirAttribs)
	{
		LOGGER.debug("Received delete request for rule with id {}.", ruleId);
		
		Rule rule = ruleRepo.findOne(ruleId);
		if (rule == null)
		{
			LOGGER.debug("Unable to find rule by id {}.", ruleId);
			return "redirect:/mycontainers";
		}
		else
		{
			String containerId = rule.getContainer().getId();
			ruleRepo.delete(rule);
			redirAttribs.addAttribute("id", containerId);
			return "redirect:/container-setup";
		}
		
	}
}
