package App.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import App.domain.FilledPosition;
import App.domain.Professor;
import App.repo.FilledPositionsRepository;
import App.repo.ProfessorRepository;
import App.repo.UserRepository;

@Service
public class ProfessorService {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ProfessorRepository professorRepo;
	
	@Autowired
	FilledPositionsRepository filledPosRepo;
	
	public void updateProfessorProfile(String professorName, String professorInterests) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Professor prof = professorRepo.findByUsername(username);
		prof.setProfessorInterests(professorInterests);
		prof.setProfessorName(professorName);
		List<FilledPosition> fps = filledPosRepo.findByProfessorUsername(username);
		for(int i = 0;i<fps.size();i++) {
			fps.get(i).setProfessorName(professorName);
			filledPosRepo.save(fps.get(i));
		}
		professorRepo.save(prof);
		
	}

	public List<FilledPosition> getProfessorPositions() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<FilledPosition> positions = filledPosRepo.findByProfessorUsername(username);
		return positions; 
	}

	public String getProfessorName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Professor prof = professorRepo.findByUsername(username);
		return prof.getProfessorName();
	}

	public String getProfessorInterests() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Professor prof = professorRepo.findByUsername(username);
		return prof.getProfessorInterests();
	}

	public void rateMotivationById(Long id,String rate) {
		FilledPosition fp = filledPosRepo.findById(id)
			    .orElseThrow(() -> new RuntimeException("Not found"));
		fp.setStudentMotivationProfessor(rate);
		filledPosRepo.save(fp);
	}
	
	public void rateEffectivenessById(Long id,String rate) {
		FilledPosition fp = filledPosRepo.findById(id)
			    .orElseThrow(() -> new RuntimeException("Not found"));
		fp.setStudentEffectivenessProfessor(rate);
		filledPosRepo.save(fp);
	}
	
	public void rateEfficiencyById(Long id,String rate) {
		FilledPosition fp = filledPosRepo.findById(id)
			    .orElseThrow(() -> new RuntimeException("Not found"));
		fp.setStudentEfficiencyProfessor(rate);
		filledPosRepo.save(fp);
	}
	
	public void rateFacilitiesById(Long id,String rate) {
		FilledPosition fp = filledPosRepo.findById(id)
			    .orElseThrow(() -> new RuntimeException("Not found"));
		fp.setCompanyFacilitiesProfessor(rate);
		filledPosRepo.save(fp);
	}
	
	public void rateGuidanceById(Long id,String rate) {
		FilledPosition fp = filledPosRepo.findById(id)
			    .orElseThrow(() -> new RuntimeException("Not found"));
		fp.setCompanyGuidanceProfessor(rate);
		filledPosRepo.save(fp);
	}


	
	

	
}
