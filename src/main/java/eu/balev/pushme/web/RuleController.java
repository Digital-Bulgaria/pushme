package eu.balev.pushme.web;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RuleController {

	private static final List<String> allRequestMethod;

	static {
		allRequestMethod = new LinkedList<>();
		allRequestMethod.add("ALL");
		for (RequestMethod method : RequestMethod.values()) {
			allRequestMethod.add(method.toString());
		}
	}

	@ModelAttribute("allMethods")
	private List<String> getAllMethods() {
		return Collections.unmodifiableList(allRequestMethod);
	}

	@RequestMapping(value = "/rules-new")
	public String newRule(RuleForm ruleForm) {
		return "rule";
	}

}
