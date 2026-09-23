package UserStoriesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;


import App.Service.CommissionerService;
import App.Service.UserService;
import App.domain.*;
import App.repo.FilledPositionsRepository;
import App.repo.OpenPositionsRepository;
import App.repo.ProfessorRepository;
import App.repo.StudentRepository;
import App.repo.UserRepository;

@SpringBootTest(classes = App.App.class)
public class CommissionerTest {

	@Autowired
	UserService userService;

	@Autowired 
	CommissionerService commissionerService;
	
	@Autowired
    UserRepository userRepository;

	@Autowired
    FilledPositionsRepository fpRepository;
	
	@Autowired
	StudentRepository stdRepository;
	
	@Autowired
	OpenPositionsRepository opRepository;
	
	@Autowired
	ProfessorRepository profRepository;
	
	@Test //US16
	void createProfileTest() {
		fpRepository.deleteAll();
		userRepository.deleteAll();

		
		Student std = new Student("Name");
		std.setApplied(1);
		stdRepository.save(std);
	
		List<Student> stds = commissionerService.getAppliedStudents();
        
        assertEquals(stds.get(0).getUsername(), "Name");
		
	}
	
	@Test //US17
	void accessToPositionsForStudentTest() {
		fpRepository.deleteAll();
		userRepository.deleteAll();
		opRepository.deleteAll();
		stdRepository.deleteAll();
		Student std = new Student("Name");
		std.setApplied(1);
		std.setStudentSkills("testing,test");
		std.setStudentInterests("1,2,3");
		std.setStudentPrefLocation("Loc");
		stdRepository.save(std);
		
		OpenPosition op = new OpenPosition();
		op.setLocation("Loc");
		op.setRequiredSkills("testing,test");
		op.setTopicsOfInterest("3,4");
		opRepository.save(op);
	
		List<OpenPosition> l = commissionerService.getPositionsBasedInterests("Name");
		List<OpenPosition> ll = commissionerService.getPositionsBasedLocation("Name");
		List<OpenPosition> lll = commissionerService.getPositionsBasedBoth("Name");
		
		
        assertEquals(l.get(0).getLocation(), "Loc");
        assertEquals(ll.get(0).getLocation(), "Loc");
        assertEquals(lll.get(0).getLocation(), "Loc");
		
	}
	
	@Test //US18
	void assignPositionToStudentTest() {
		fpRepository.deleteAll();
		userRepository.deleteAll();
		opRepository.deleteAll();
		stdRepository.deleteAll();
		Student std = new Student("Name");
		std.setApplied(1);
		std.setStudentSkills("testing,test");
		std.setStudentInterests("1,2,3");
		std.setStudentPrefLocation("Loc");
		stdRepository.save(std);
		
		OpenPosition op = new OpenPosition();
		op.setLocation("Loc");
		op.setRequiredSkills("testing,test");
		op.setTopicsOfInterest("3,4");
		opRepository.save(op);
	
		commissionerService.assignStudent("Name", op.getId());
		
		FilledPosition fp = fpRepository.findAll().get(0);
		
        
        assertEquals(fp.getStudentUsername(), "Name");
		
	}
	
	@Test //US19
	void assignProfessorToTraineeshipTest() {
		fpRepository.deleteAll();
		userRepository.deleteAll();
		opRepository.deleteAll();
		stdRepository.deleteAll();
		profRepository.deleteAll();
		Professor prof = new Professor("Name");
		prof.setProfessorName("Professor");
		prof.setProfessorInterests("1,2,3");
		prof.setProfessorLoad(0);
		profRepository.save(prof);
		
		FilledPosition fp = new FilledPosition();
		fp.setTopicsOfInterest("3,2");
		fpRepository.save(fp);
		
		commissionerService.allocateInterests(fp.getId());
		
		FilledPosition tested = commissionerService.getFilledPositionById(fp.getId());
        assertEquals(tested.getProfessorUsername(), "Name");
        
        fp.setProfessorName(null);
        fpRepository.save(fp);
        commissionerService.allocateLoad(fp.getId());
        tested = commissionerService.getFilledPositionById(fp.getId());
        assertEquals(tested.getProfessorUsername(), "Name");
		
	}
	@Test //US20
	void acessAllTraineeshipsIngProgressTest() {
		fpRepository.deleteAll();
		userRepository.deleteAll();
		opRepository.deleteAll();
		stdRepository.deleteAll();
		profRepository.deleteAll();
		
		FilledPosition fp = new FilledPosition();
		fp.setLocation("Loc");
		fpRepository.save(fp);
		
		List<FilledPosition> fps = commissionerService.getAllFilledPositions();
        
        
        assertEquals(fps.get(0).getLocation(), "Loc");
	}

	@Test //US20
	void markEvaluationTest() {
		fpRepository.deleteAll();
		userRepository.deleteAll();
		opRepository.deleteAll();
		stdRepository.deleteAll();
		profRepository.deleteAll();
		
		FilledPosition fp = new FilledPosition();
		fp.setStudentEffectivenessProfessor("1");
		fp.setStudentMotivationProfessor("2");
		fp.setStudentEfficiencyProfessor("3");
		fp.setCompanyFacilitiesProfessor("4");
		fp.setCompanyGuidanceProfessor("5");
		fp.setStudentEffectivenessCompany("4");
		fp.setStudentMotivationCompany("3");
		fp.setStudentEfficiencyCompany("2");
		fpRepository.save(fp);
		
		
		commissionerService.rateTraineeship(fp.getId(),"fail");
		 
		List<FilledPosition> fps = commissionerService.getAllFilledPositions();
        
        
        assertEquals(fps.get(0).getStudentEffectivenessProfessor(), "1");
        assertEquals(fps.get(0).getStudentMotivationProfessor(), "2");
        assertEquals(fps.get(0).getStudentEfficiencyProfessor(), "3");
        assertEquals(fps.get(0).getCompanyFacilitiesProfessor(), "4");
        assertEquals(fps.get(0).getCompanyGuidanceProfessor(), "5");
        assertEquals(fps.get(0).getStudentEffectivenessCompany(), "4");
        assertEquals(fps.get(0).getStudentMotivationCompany(), "3");
        assertEquals(fps.get(0).getStudentEfficiencyCompany(), "2");
        
       
        
        assertEquals(fps.get(0).getRate(), "fail");
        
	}
}
