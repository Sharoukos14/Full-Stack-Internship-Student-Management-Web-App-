package UserStoriesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import App.Service.CompanyService;
import App.Service.UserService;
import App.domain.*;
import App.repo.CompanyRepository;
import App.repo.FilledPositionsRepository;
import App.repo.OpenPositionsRepository;
import App.repo.UserRepository;

@SpringBootTest(classes = App.App.class)
public class CompanyTest {

	@Autowired
	UserService userService;

	@Autowired 
	CompanyService companyService;
	
	@Autowired
    CompanyRepository cmpRepository;
	
	@Autowired
    UserRepository userRepository;

	@Autowired
    FilledPositionsRepository fpRepository;
	
	@Autowired
	OpenPositionsRepository opRepository;
	
	@Test //US7
	void createProfileTest() {
		fpRepository.deleteAll();
		cmpRepository.deleteAll();
		userRepository.deleteAll();
		opRepository.deleteAll();
		User user = new User();
		user.setUsername("Name");
		user.setPassword("password");
		Role r = Role.ROLE_COMPANY;
		user.setRole(r);
		userService.register(user);
		UsernamePasswordAuthenticationToken auth =
		        new UsernamePasswordAuthenticationToken("Name", "password", List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(auth);
		SecurityContextHolder.setContext(context);
		
		companyService.updateCompanyProfile("test", "test");
	
		
		Company tested = cmpRepository.findByUsername("Name");
        
        assertEquals(tested.getLocation(), "test");
		
	}
	
	@Test //US8 US10
	void makeAndAcessToOpenPositionsTest() {
		fpRepository.deleteAll();
		cmpRepository.deleteAll();
		userRepository.deleteAll();
		opRepository.deleteAll();
		User user = new User();
		user.setUsername("Name");
		user.setPassword("password");
		Role r = Role.ROLE_COMPANY;
		user.setRole(r);
		userService.register(user);
		UsernamePasswordAuthenticationToken auth =
		        new UsernamePasswordAuthenticationToken("Name", "password", List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(auth);
		SecurityContextHolder.setContext(context);
		    
		companyService.makePosition("test", "test", "test", "test", "test"); //US10
		
		List<OpenPosition> ops = companyService.allMyOpenPositions(); //US8 and if US10 is executed correctly 
        
        assertEquals(ops.get(0).getJobDescription(),"test"); //If US8 is executed correctly
		
	}
	
	@Test //US9
	void accessToPositionsTest() {
		fpRepository.deleteAll();
		cmpRepository.deleteAll();
		userRepository.deleteAll();
		opRepository.deleteAll();
		User user = new User();
		user.setUsername("Name");
		user.setPassword("password");
		Role r = Role.ROLE_COMPANY;
		user.setRole(r);
		userService.register(user);
		UsernamePasswordAuthenticationToken auth =
		        new UsernamePasswordAuthenticationToken("Name", "password", List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(auth);
		SecurityContextHolder.setContext(context);
		    
		FilledPosition fp = new FilledPosition();
		fp.setCompanyUsername("Name");
		fpRepository.save(fp);
		
		FilledPosition tested = companyService.getFilledPositions().get(0);
        
        assertEquals(tested.getCompanyUsername(), "Name");
		
	}

	@Test //US11
	void deletePositionTest() {
		fpRepository.deleteAll();
		cmpRepository.deleteAll();
		userRepository.deleteAll();
		opRepository.deleteAll();
		User user = new User();
		user.setUsername("Name");
		user.setPassword("password");
		Role r = Role.ROLE_COMPANY;
		user.setRole(r);
		userService.register(user);
		UsernamePasswordAuthenticationToken auth =
		        new UsernamePasswordAuthenticationToken("Name", "password", List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(auth);
		SecurityContextHolder.setContext(context);
		    
		companyService.makePosition("test", "test", "test", "test", "test");
		List<OpenPosition> ops = opRepository.findByCompanyUsername("Name");
	
		companyService.deleteById(ops.get(0).getId());
		
		List<OpenPosition> tested = companyService.allMyOpenPositions(); 
        
        assertTrue(tested.isEmpty());
		
	}
	
	@Test //US12
	void rateTest() {
		fpRepository.deleteAll();
		cmpRepository.deleteAll();
		userRepository.deleteAll();
		opRepository.deleteAll();
		User user = new User();
		user.setUsername("Name");
		user.setPassword("password");
		Role r = Role.ROLE_COMPANY;
		user.setRole(r);
		userService.register(user);
		UsernamePasswordAuthenticationToken auth =
		        new UsernamePasswordAuthenticationToken("Name", "password", List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(auth);
		SecurityContextHolder.setContext(context);
		    
		FilledPosition fp = new FilledPosition();
		fp.setCompanyUsername("Name");
		fpRepository.save(fp);
			
		companyService.rateEffectivenessById(fp.getId(),"5");
		companyService.rateMotivationById(fp.getId(),"4");
		companyService.rateEfficiencyById(fp.getId(),"3");
        
		FilledPosition tested = fpRepository.findByCompanyUsername("Name").get(0);
        assertEquals(tested.getStudentEffectivenessCompany(),"5");
        assertEquals(tested.getStudentMotivationCompany(),"4");
        assertEquals(tested.getStudentEfficiencyCompany(),"3");
		
	}
	
}
