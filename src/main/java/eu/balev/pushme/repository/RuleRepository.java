package eu.balev.pushme.repository;

import org.springframework.data.repository.CrudRepository;

import eu.balev.pushme.domain.Rule;

public interface RuleRepository extends CrudRepository<Rule, Long> {
	
}