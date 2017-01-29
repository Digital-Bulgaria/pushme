package eu.balev.pushme.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.User;

@Repository
public interface ContainerRepository extends CrudRepository<Container, String> {
	
	List<Container> findByUser(User user);
	
	Long countByUser(User user);
}
