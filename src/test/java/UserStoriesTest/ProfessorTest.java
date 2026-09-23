package UserStoriesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;


import App.Service.ProfessorService;
import App.Service.UserService;
import App.domain.*;
import App.repo.FilledPositionsRepository;
import App.repo.ProfessorRepository;
import App.repo.UserRepository;

@SpringBootTest(classes = App.App.class)
public class ProfessorTest {

	@Autowired
	UserService userService;

	@Autowired 
	ProfessorService professorService;
	
	@Autowired
    UserRepository userRepository;

	@Autowired
    FilledPositionsRepository fpRepository;
	
	@Autowired
	ProfessorRepository profRepository;
	
	@Test //US13
	void createProfileTest() {
		fpRepository.deleteAll();
		profRepository.deleteAll();
		userRepository.deleteAll();

		User user = new User();
		user.setUsername("Name");
		user.setPassword("password");
		Role r = Role.ROLE_PROFESSOR;
		user.setRole(r);
		userService.register(user);
		UsernamePasswordAuthenticationToken auth =
		        new UsernamePasswordAuthenticationToken("Name", "password", List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
		    SecurityContext context = SecurityContextHolder.createEmptyContext();
		    context.setAuthentication(auth);
		    SecurityContextHolder.setContext(context);
		
		professorService.updateProfessorProfile("test", "test,test");
	
		
		Professor tested = profRepository.findByUsername("Name");
        
        assertEquals(tested.getProfessorInterests(), "test,test");
		
	}
	

	
	@Test //US14
	void accessToPositionsTest() {
		fpRepository.deleteAll();
		profRepository.deleteAll();
		userRepository.deleteAll();
		
		User user = new User();
		user.setUsername("Name");
		user.setPassword("password");
		Role r = Role.ROLE_PROFESSOR;
		user.setRole(r);
		userService.register(user);
		UsernamePasswordAuthenticationToken auth =
		        new UsernamePasswordAuthenticationToken("Name", "password", List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
		    SecurityContext context = SecurityContextHolder.createEmptyContext();
		    context.setAuthentication(auth);
		    SecurityContextHolder.setContext(context);
		    
		FilledPosition fp = new FilledPosition();
		fp.setProfessorUsername("Name");
		fpRepository.save(fp);
		
		FilledPosition tested = professorService.getProfessorPositions().get(0);
        
        assertEquals(tested.getProfessorUsername(), "Name");
		
	}
	
	@Test //US15
	void rateTest() {
		fpRepository.deleteAll();
		profRepository.deleteAll();
		userRepository.deleteAll();
		
		User user = new User();
		user.setUsername("Name");
		user.setPassword("password");
		Role r = Role.ROLE_PROFESSOR;
		user.setRole(r);
		userService.register(user);
		UsernamePasswordAuthenticationToken auth =
		        new UsernamePasswordAuthenticationToken("Name", "password", List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(auth);
		SecurityContextHolder.setContext(context);
		    
		FilledPosition fp = new FilledPosition();
		fp.setProfessorUsername("Name");
		fpRepository.save(fp);
		
		professorService.rateEffectivenessById(fp.getId(), "5");
		professorService.rateMotivationById(fp.getId(), "4");
		professorService.rateEfficiencyById(fp.getId(), "3");
		professorService.rateFacilitiesById(fp.getId(), "2");
		professorService.rateGuidanceById(fp.getId(), "1");
        
		FilledPosition tested = fpRepository.findByProfessorUsername("Name").get(0);
        assertEquals(tested.getStudentEffectivenessProfessor(),"5");
        assertEquals(tested.getStudentMotivationProfessor(),"4");
        assertEquals(tested.getStudentEfficiencyProfessor(),"3");
        assertEquals(tested.getCompanyFacilitiesProfessor(),"2");
        assertEquals(tested.getCompanyGuidanceProfessor(),"1");
		
	}
}
