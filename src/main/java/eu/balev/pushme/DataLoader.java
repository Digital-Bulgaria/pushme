package eu.balev.pushme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import eu.balev.pushme.domain.User;
import eu.balev.pushme.repository.UserRepository;

@Component
public class DataLoader  implements ApplicationRunner {

    private UserRepository userRepository;

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args) {
    	
    	User testUser = new User();
    	
    	testUser.setEmail("lucho@push.me");
    	testUser.setPasswordHash(new BCryptPasswordEncoder().encode("dummy"));
    	
        userRepository.save(testUser);
    }
}
