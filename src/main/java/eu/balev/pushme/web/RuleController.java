package eu.balev.pushme.web;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eu.balev.pushme.service.RuleService;

@Controller
public class RuleController {

	@Autowired
	private RuleService ruleService;

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
		
		model.addAttribute("containerid", ruleForm.getContainerId());
		
		return "rulecreated";
	}
}
