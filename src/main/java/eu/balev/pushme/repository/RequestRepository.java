package eu.balev.pushme.repository;

import org.springframework.data.repository.CrudRepository;

import eu.balev.pushme.domain.Request;

public interface RequestRepository extends CrudRepository<Request, Long> {
	
}