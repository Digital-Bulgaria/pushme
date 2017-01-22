package eu.balev.pushme.service.rule;

import eu.balev.pushme.web.RuleForm;

/**
 * Defines methods for manipulation of container rules.
 */
public interface RuleService {

	/**
	 * Creates or updates a rule.
	 * 
	 * @param ruleForm the rule representation
	 */
	void createOrUpdate(RuleForm ruleForm);
}
