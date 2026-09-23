package App.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import App.domain.FilledPosition;
import App.domain.Student;
import App.repo.FilledPositionsRepository;
import App.repo.StudentRepository;

@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	FilledPositionsRepository filledPosRepo;
	
	public String getStudentName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Student s = studentRepo.findByUsername(username);
		return s.getStudentName();
	}

	public String getStudentUniId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Student s = studentRepo.findByUsername(username);
		return s.getStudentUniId();
	}

	public String getStudentInterests() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Student s = studentRepo.findByUsername(username);
		return s.getStudentInterests();
	}

	public String getStudentSkills() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Student s = studentRepo.findByUsername(username);
		return s.getStudentSkills();
	}

	public String getStudentPrefLocation() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Student s = studentRepo.findByUsername(username);
		return s.getStudentPrefLocation();
	}

	public void updateStudentProfile(String studentName, String uniId, String interests, String skills,
			String prefLocation) 
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Student s = studentRepo.findByUsername(username);
		s.setStudentName(studentName);
		s.setStudentUniId(uniId);
		s.setStudentInterests(interests);
		s.setStudentSkills(skills);
		s.setStudentPrefLocation(prefLocation);
		List<FilledPosition> fps = filledPosRepo.findByStudentUsername(username);
		for(int i =0;i<fps.size();i++) {
			fps.get(i).setStudentName(studentName);
			filledPosRepo.save(fps.get(i));
		}
		studentRepo.save(s);
	}

	public void applyForTraineeship() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Student s = studentRepo.findByUsername(username);
		s.setApplied(1);
		studentRepo.save(s);
		
	}

	public ArrayList<String> getLogbook() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Student s = studentRepo.findByUsername(username);
		ArrayList<String> ret = s.getLogbook();
		return ret;
	}

	public void updateStudentLogbook(String adder) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Student s = studentRepo.findByUsername(username);
		s.addLogbook(adder);
		studentRepo.save(s);
	}
	
	public Student getStudent(String username) {
		Student s = studentRepo.findByUsername(username);
		return s;
	}
	
	
	
	
}
