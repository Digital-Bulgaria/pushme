package eu.balev.pushme.repository;

import org.springframework.data.repository.CrudRepository;

import eu.balev.pushme.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
}