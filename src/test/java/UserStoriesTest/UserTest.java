package UserStoriesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import App.Service.UserService;
import App.domain.*;
import App.repo.StudentRepository;
import App.repo.UserRepository;

@SpringBootTest(classes = App.App.class)
public class UserTest {

	@Autowired 
	UserService userService;
	
	@Autowired
    UserRepository userRepository;
	
	@Autowired
    StudentRepository stdRepository;

	@Test //US1
	void registerTest() {
		User user = new User();
		user.setUsername("Name");
		user.setPassword("password");
		Role r = Role.ROLE_STUDENT;
		user.setRole(r);
		
		userRepository.deleteAll();
		userService.register(user);
		
        User result = userRepository.findByUsername(user.getUsername());
        assertEquals("Name", result.getUsername());
		
	}
	
	@Test //US2
	void loadTest() {
		User user = new User();
		user.setUsername("Name");
		user.setPassword("password");
		Role r = Role.ROLE_STUDENT;
		user.setRole(r);
		
		userRepository.deleteAll();
		stdRepository.deleteAll();
		userService.save(user);
		
        UserDetails result = userService.loadUserByUsername("Name");
        assertEquals("Name",result.getUsername());
	}
	
	
}
