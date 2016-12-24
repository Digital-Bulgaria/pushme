package eu.balev.pushme.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.balev.pushme.domain.Container;

@Repository
public interface ContainerRepository extends CrudRepository<Container, String> {
	
}
