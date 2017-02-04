package eu.balev.pushme.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.User;

@Repository
public interface ContainerRepository extends CrudRepository<Container, String> {
	
	List<Container> findByUser(User user);
	
	Long countByUser(User user);
	
	@Query("FROM Container c WHERE c.user IS NULL ORDER BY c.dateCreated ASC")
    List<Container> findOldestAnonymousContainers(Pageable pageable);
}
