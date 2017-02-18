package eu.balev.pushme.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.balev.pushme.domain.Container;
import eu.balev.pushme.domain.Request;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {
	
	@Query("FROM Request r WHERE r.container = ? ORDER BY r.dateCreated ASC")
    List<Request> findOldestRequests(Container c, Pageable pageable);

}