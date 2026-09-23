package App.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import App.domain.Company;
import App.domain.FilledPosition;
import App.domain.OpenPosition;
import App.repo.CompanyRepository;
import App.repo.FilledPositionsRepository;
import App.repo.OpenPositionsRepository;

@Service
public class CompanyService {
	@Autowired
	FilledPositionsRepository filledPosRepo;
	
	@Autowired
	CompanyRepository compRepo;
	
	@Autowired
	OpenPositionsRepository posRepo;
	
	
	public void updateCompanyProfile(String companyName,String companyLocation) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Company comp = compRepo.findByUsername(username);
		comp.setLocation(companyLocation);
		comp.setName(companyName);
		List<OpenPosition> ops = posRepo.findByCompanyUsername(username);
		for(int i = 0;i<ops.size();i++) {
			ops.get(i).setCompanyName(companyName);
			ops.get(i).setLocation(companyLocation);
		}
		List<FilledPosition> fps = filledPosRepo.findByCompanyUsername(username);
		for(int i = 0;i<fps.size();i++) {
			fps.get(i).setCompanyName(companyName);
			fps.get(i).setLocation(companyLocation);
			filledPosRepo.save(fps.get(i));
		}
		compRepo.save(comp);
	}
	
	public String getCompanyName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Company comp = compRepo.findByUsername(username);
		return comp.getName();
	}
	 
	public String getCompanyLocation() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Company comp = compRepo.findByUsername(username);
		return comp.getLocation();
	}

	public void makePosition(String startDate, String endDate, String jobDescription, String requiredSkills,
			String topicsOfInterest) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		OpenPosition op = new OpenPosition(username);
		Company cp = compRepo.findByUsername(username);
		op.setStartDate(startDate);
		op.setEndDate(endDate);
		op.setJobDescription(jobDescription);
		op.setRequiredSkills(requiredSkills);
		op.setTopicsOfInterest(topicsOfInterest);
		op.setLocation(cp.getLocation());
		op.setCompanyName(cp.getName());
		op.setLocation(cp.getLocation());
		posRepo.save(op);
	}
	
	public List<OpenPosition> allMyOpenPositions(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<OpenPosition> positions = posRepo.findByCompanyUsername(username);
		return positions; 
		
	}

	public List<FilledPosition> getFilledPositions() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<FilledPosition> positions = filledPosRepo.findByCompanyUsername(username);
		return positions; 
	}

	public void deleteById(Long id) {
		posRepo.deleteById(id);
		
	}

	public void rateMotivationById(Long id,String rate) {
		FilledPosition fp = filledPosRepo.findById(id)
			    .orElseThrow(() -> new RuntimeException("Not found"));
		fp.setStudentMotivationCompany(rate);
		filledPosRepo.save(fp);
	}
	
	public void rateEffectivenessById(Long id,String rate) {
		FilledPosition fp = filledPosRepo.findById(id)
			    .orElseThrow(() -> new RuntimeException("Not found"));
		fp.setStudentEffectivenessCompany(rate);
		filledPosRepo.save(fp);
	}
	
	public void rateEfficiencyById(Long id,String rate) {
		FilledPosition fp = filledPosRepo.findById(id)
			    .orElseThrow(() -> new RuntimeException("Not found"));
		fp.setStudentEfficiencyCompany(rate);
		filledPosRepo.save(fp);
	}
	
	
	
}
