package eu.balev.pushme.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import eu.balev.pushme.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	Optional<User> findOneByEmail(String email);
	
}