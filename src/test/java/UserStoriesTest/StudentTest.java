package UserStoriesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import App.Service.StudentService;
import App.Service.UserService;
import App.domain.*;
import App.repo.StudentRepository;
import App.repo.UserRepository;

@SpringBootTest(classes = App.App.class)
public class StudentTest {

	@Autowired
	UserService userService;

	@Autowired 
	StudentService studentService;
	
	@Autowired
    StudentRepository stdRepository;
	
	@Autowired
    UserRepository userRepository;

	@Test //US4
	void createProfileTest() {
		
		stdRepository.deleteAll();
		userRepository.deleteAll();
		User user = new User();
		user.setUsername("Name");
		user.setPassword("password");
		Role r = Role.ROLE_STUDENT;
		user.setRole(r);
		userService.register(user);
		UsernamePasswordAuthenticationToken auth =
		        new UsernamePasswordAuthenticationToken("Name", "password", List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
		    SecurityContext context = SecurityContextHolder.createEmptyContext();
		    context.setAuthentication(auth);
		    SecurityContextHolder.setContext(context);
		
		studentService.updateStudentProfile("test", "test", "test", "test", "test");
	
		
		Student tested = stdRepository.findByUsername("Name");
        
        assertEquals(tested.getStudentPrefLocation(), "test");
		
	}
	
	@Test //US5
	void applyTest() {
		
		stdRepository.deleteAll();
		userRepository.deleteAll();
		User user = new User();
		user.setUsername("Name");
		user.setPassword("password");
		Role r = Role.ROLE_STUDENT;
		user.setRole(r);
		userService.register(user);
		UsernamePasswordAuthenticationToken auth =
		        new UsernamePasswordAuthenticationToken("Name", "password", List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
		    SecurityContext context = SecurityContextHolder.createEmptyContext();
		    context.setAuthentication(auth);
		    SecurityContextHolder.setContext(context);
		    
		studentService.applyForTraineeship();
	
		
		Student tested = stdRepository.findByUsername("Name");
        
        assertEquals(tested.isApplied(), 1);
		
	}
	
	@Test //US5
	void logbookTest() {
		
		stdRepository.deleteAll();
		userRepository.deleteAll();
		User user = new User();
		user.setUsername("Name");
		user.setPassword("password");
		Role r = Role.ROLE_STUDENT;
		user.setRole(r);
		userService.register(user);
		UsernamePasswordAuthenticationToken auth =
		        new UsernamePasswordAuthenticationToken("Name", "password", List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
		    SecurityContext context = SecurityContextHolder.createEmptyContext();
		    context.setAuthentication(auth);
		    SecurityContextHolder.setContext(context);
		    
		  
		studentService.updateStudentLogbook("test");
		studentService.updateStudentLogbook("test");
		
		ArrayList<String> tester = new ArrayList<String>();
		tester.add("test");
		tester.add("test");
		
		Student tested = stdRepository.findByUsername("Name");
        
        assertEquals(tested.getLogbook(), tester);
		
	}


	
}
