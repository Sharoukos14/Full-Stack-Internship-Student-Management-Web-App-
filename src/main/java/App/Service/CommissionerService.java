package App.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import App.domain.FilledPosition;
import App.domain.OpenPosition;
import App.domain.Professor;
import App.domain.Student;
import App.repo.FilledPositionsRepository;
import App.repo.OpenPositionsRepository;
import App.repo.ProfessorRepository;
import App.repo.StudentRepository;

@Service
public class CommissionerService {
	
	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	OpenPositionsRepository posRepo;
	
	@Autowired
	FilledPositionsRepository filledPosRepo;
	
	@Autowired 
	ProfessorRepository profRepo;

	public List<Student> getAppliedStudents() {
		List<Student> students = studentRepo.findAll();
		ArrayList<Student> ret = new ArrayList<Student>();
		for(int i = 0;i<students.size();i++) {
			if(students.get(i).isApplied() == 1) {
				ret.add(students.get(i));
			}
		}
		return ret;
	}

	public void assignStudent(String sName, Long id) {
		Student s = studentRepo.findByUsername(sName);
		OpenPosition op = posRepo.findById(id)
			    .orElseThrow(() -> new RuntimeException("Not found"));
		FilledPosition fp = new FilledPosition(op);
		fp.setStudentName(s.getStudentName());
		fp.setStudentUsername(sName);
		filledPosRepo.save(fp);
		posRepo.deleteById(id);
		s.setApplied(0);
		studentRepo.save(s);
	}

	public List<FilledPosition> getAllFilledPositions() {return filledPosRepo.findAll();}

	public FilledPosition getFilledPositionById(Long id) {return filledPosRepo.findById(id)  .orElseThrow(() -> new RuntimeException("Not found"));}

	public FilledPosition rateTraineeship(Long id,String rate) {
		FilledPosition fp = filledPosRepo.findById(id)
			    .orElseThrow(() -> new RuntimeException("Not found"));
		fp.setRate(rate);
		filledPosRepo.save(fp);
		if(fp.getProfessorName() != null) {
			String profUsername = fp.getProfessorUsername();
			Professor prof = profRepo.findByUsername(profUsername);
			prof.setProfessorLoad(prof.getProfessorLoad() - 1);
			profRepo.save(prof);
		}
		return fp;
	}

	public void deleteById(Long id) {filledPosRepo.deleteById(id);}

	public List<OpenPosition> getPositionsBasedInterests(String id) {
		Student s = studentRepo.findByUsername(id);
		List<OpenPosition> ll = posRepo.findAll();
		ArrayList<OpenPosition> ret = new ArrayList<OpenPosition>();
		String[] stdSkills = s.getStudentSkills().split(",");
		String[] stdInts = s.getStudentInterests().split(",");
		
		
		for(int i = 0;i<stdInts.length;i++) {
			stdInts[i] = stdInts[i].toLowerCase().trim();
		}
		
		for(int i = 0;i<stdSkills.length;i++) {
			stdSkills[i] = stdSkills[i].toLowerCase().trim();
		}
		
		for(int i = 0;i<ll.size();i++) {
			
			OpenPosition op = ll.get(i);
			String[] posInts = op.getTopicsOfInterest().split(",");
			String[] posSkills = op.getRequiredSkills().split(",");
			for(int j = 0;j<posInts.length;j++) {
				posInts[j] = posInts[j].toLowerCase().trim();
			}
			
			int skills = posSkills.length;
			for(int j = 0;j<posSkills.length;j++) {
				posSkills[j] = posSkills[j].toLowerCase().trim();
			}
			int skillsInd = 0;
			for(int j = 0;j<posSkills.length;j++) {
				for(int k = 0;k<stdSkills.length;k++) {
					if(stdSkills[k].equals(posSkills[j])) {
						skillsInd++;
					}
				}
				
			}
			
			
			double js = getJaccard(stdInts,posInts);
			
			
			
			if(js >=0.2 && skillsInd == skills) {
				ret.add(op);
			}
		}
		return ret;
	}

	private double getJaccard(String[] a, String[] b) {
	    Set<String> setA = Arrays.stream(a).map(String::toLowerCase).map(String::trim).collect(Collectors.toSet());
	    Set<String> setB = Arrays.stream(b).map(String::toLowerCase).map(String::trim).collect(Collectors.toSet());

	    Set<String> intersection = new HashSet<>(setA);
	    intersection.retainAll(setB);

	    Set<String> union = new HashSet<>(setA);
	    union.addAll(setB);

	    return union.isEmpty() ? 0 : (double) intersection.size() / union.size();
	}

	public List<OpenPosition> getPositionsBasedLocation(String id) {
		Student s = studentRepo.findByUsername(id);
		List<OpenPosition> ll = posRepo.findAll();
		ArrayList<OpenPosition> ret = new ArrayList<OpenPosition>();
		
		String[] stdSkills = s.getStudentSkills().split(",");
		for(int j = 0;j<stdSkills.length;j++) {
			stdSkills[j] = stdSkills[j].toLowerCase().trim();
		}
		for(int i = 0;i<ll.size();i++) {
			String[] posSkills = ll.get(i).getRequiredSkills().split(",");
			int skills = posSkills.length;
			int skillsInd = 0;
			for(int j = 0;j<posSkills.length;j++) {
				posSkills[j] = posSkills[j].toLowerCase().trim();
				
			}
			for(int j = 0;j<posSkills.length;j++) {
				for(int k = 0;k<stdSkills.length;k++) {
					if(stdSkills[k].equals(posSkills[j])) {
						skillsInd++;
					}
				}
				
			}
			if(s.getStudentPrefLocation().equals(ll.get(i).getLocation()) && skillsInd == skills) {
				ret.add(ll.get(i));
			}
		}
		return ret;
	}

	public List<OpenPosition> getPositionsBasedBoth(String id) {
		Student s = studentRepo.findByUsername(id);
		ArrayList<OpenPosition> ret = new ArrayList<OpenPosition>();
		List<OpenPosition> basedInterests = getPositionsBasedInterests(id);
		for(int i = 0;i<basedInterests.size();i++) {
			if(s.getStudentPrefLocation().equals(basedInterests.get(i).getLocation())) {
				ret.add(basedInterests.get(i));
			}
		}
		
		return ret;
	}

	public void allocateInterests(Long id) {
		FilledPosition fp = filledPosRepo.findById(id) .orElseThrow(() -> new RuntimeException("Not found"));
		if(fp.getProfessorName() == null) {
			List<Professor> ll = profRepo.findAll();
			String[] fpInts = fp.getTopicsOfInterest().split(",");
			Professor ret = null;
			double profLastJS = 0;
		
			for(int i = 0;i<fpInts.length;i++) {
				fpInts[i] = fpInts[i].toLowerCase().trim();
			}
			
			for(int i =0;i<ll.size();i++) {
				Professor prof = ll.get(i);
				String[] profInts = prof.getProfessorInterests().split(",");
				for(int j = 0;j<profInts.length;j++) {
					profInts[j] = profInts[j].toLowerCase().trim();
				}
				
				double js = getJaccard(fpInts,profInts);
				
				if(js >= profLastJS) {
					ret = prof;
				}
				
				
				
			}
			
			fp.setProfessorName(ret.getProfessorName());
			fp.setProfessorUsername(ret.getUsername());
			ret.setProfessorLoad(ret.getProfessorLoad() + 1);
			filledPosRepo.save(fp);
			profRepo.save(ret);
		}
		
	}

	public void allocateLoad(Long id) {
		FilledPosition fp = filledPosRepo.findById(id) .orElseThrow(() -> new RuntimeException("Not found"));
		if(fp.getProfessorName()==null) {
			List<Professor> ll = profRepo.findAll();
			Professor ret = ll.get(0);
			for(int i =0;i<ll.size();i++) {
				if(ll.get(i).getProfessorLoad() < ret.getProfessorLoad()) {
					ret = ll.get(i);
				}
			}
			fp.setProfessorName(ret.getProfessorName());
			fp.setProfessorUsername(ret.getUsername());
			ret.setProfessorLoad(ret.getProfessorLoad() + 1);
			filledPosRepo.save(fp);
			profRepo.save(ret);
		}

		
	}

	
	
}
