package eu.balev.pushme.service.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.Rule;
import eu.balev.pushme.repository.ContainerRepository;
import eu.balev.pushme.repository.RuleRepository;
import eu.balev.pushme.web.RuleForm;

@Service
public class RuleServiceImpl implements RuleService {

	@Autowired
	private RuleRepository ruleRepo;
	
	@Autowired
	private ContainerRepository containerRepo;

	@Override
	public void createOrUpdate(RuleForm ruleForm) {
		// TODO dozer?
		
		Container ctnr = containerRepo.findOne(ruleForm.getContainerId());
		// TODO: error check
		
		Rule rule = new Rule();
		
		rule.setContainer(ctnr);
		rule.setRequestMethod(ruleForm.getRequestMethod());
		rule.setResponseCode(ruleForm.getResponseCode());
		rule.setResponseBody(ruleForm.getResponseBody());

		ruleRepo.save(rule);
	}
}
