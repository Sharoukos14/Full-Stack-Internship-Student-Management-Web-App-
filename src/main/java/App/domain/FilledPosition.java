package App.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class FilledPosition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	private String companyUsername;
	private String companyName;
	private String professorUsername;
	private String professorName;
	private String studentUsername;
	private String studentName;
	private String startDate;
	private String endDate;
	private String jobDescription;
	private String requiredSkills;
	private String topicsOfInterest;
	private String location;
	
	private String rate;
	
	private String studentMotivationCompany;
	private String studentEffectivenessCompany;
	private String studentEfficiencyCompany;
	private String studentMotivationProfessor;
	private String studentEffectivenessProfessor;
	private String studentEfficiencyProfessor;
	private String companyFacilitiesProfessor;
	private String companyGuidanceProfessor;
	
	
	public FilledPosition(OpenPosition op) {
		this.setCompanyName(op.getCompanyName());
		this.setCompanyUsername(op.getCompanyUsername());
		this.setStartDate(op.getStartDate());
		this.setEndDate(op.getEndDate());
		this.setJobDescription(op.getJobDescription());
		this.setRequiredSkills(op.getRequiredSkills());
		this.setTopicsOfInterest(op.getTopicsOfInterest());
		this.setLocation(op.getLocation());
	}
	
	public FilledPosition() {
		
	}
	
	public long getId() {
		return id;
	}
	
	public String getCompanyUsername() {return companyUsername;}
	public void setCompanyUsername(String companyUsername) {this.companyUsername = companyUsername;}
	
	public String getStartDate() {return startDate;}
	public void setStartDate(String startdate) {this.startDate = startdate;}
	
	public String getEndDate() {return endDate;}
	public void setEndDate(String endDate) {this.endDate = endDate;}
	
	public String getJobDescription() {return jobDescription;}
	public void setJobDescription(String jobDescription) {this.jobDescription = jobDescription;}
	
	public String getRequiredSkills() {return requiredSkills;}
	public void setRequiredSkills(String requiredSkills) {this.requiredSkills = requiredSkills;}
	
	public String getTopicsOfInterest() {return topicsOfInterest;}
	public void setTopicsOfInterest(String topicsOfInterest) {this.topicsOfInterest = topicsOfInterest;}

	public String getStudentName() {return studentName;}
	public void setStudentName(String studentName) {this.studentName = studentName;}

	public String getProfessorName() {return professorName;}
	public void setProfessorName(String professorName) {this.professorName = professorName;}

	public String getStudentMotivationCompany() {return studentMotivationCompany;}
	public void setStudentMotivationCompany(String studentMotivationCompany) {this.studentMotivationCompany = studentMotivationCompany;}

	public String getStudentEffectivenessCompany() {return studentEffectivenessCompany;}
	public void setStudentEffectivenessCompany(String stuentEffectivenessCompany) {this.studentEffectivenessCompany = stuentEffectivenessCompany;}

	public String getStudentEfficiencyCompany() {return studentEfficiencyCompany;}
	public void setStudentEfficiencyCompany(String studentEfficiencyCompany) {this.studentEfficiencyCompany = studentEfficiencyCompany;}

	public String getStudentMotivationProfessor() {return studentMotivationProfessor;}
	public void setStudentMotivationProfessor(String studentMotivationProfessor) {this.studentMotivationProfessor = studentMotivationProfessor;}

	public String getStudentEffectivenessProfessor() {return studentEffectivenessProfessor;}
	public void setStudentEffectivenessProfessor(String studentEffectivenessProfessor) {this.studentEffectivenessProfessor = studentEffectivenessProfessor;}

	public String getStudentEfficiencyProfessor() {return studentEfficiencyProfessor;}
	public void setStudentEfficiencyProfessor(String studentEfficiencyProfessor) {this.studentEfficiencyProfessor = studentEfficiencyProfessor;}

	public String getCompanyFacilitiesProfessor() {return companyFacilitiesProfessor;}
	public void setCompanyFacilitiesProfessor(String companyFacilitiesProfessor) {this.companyFacilitiesProfessor = companyFacilitiesProfessor;}

	public String getCompanyGuidanceProfessor() {return companyGuidanceProfessor;}
	public void setCompanyGuidanceProfessor(String companyGuidanceProfessor) {this.companyGuidanceProfessor = companyGuidanceProfessor;}

	public String getProfessorUsername() {return professorUsername;}
	public void setProfessorUsername(String professorUsername) {this.professorUsername = professorUsername;}
	
	public String getStudentUsername() {return studentUsername;}
	public void setStudentUsername(String studentUsername) {this.studentUsername = studentUsername;}

	public String getCompanyName() {return companyName;}
	public void setCompanyName(String companyName) {this.companyName = companyName;}

	public String getLocation() {return location;}
	public void setLocation(String location) {this.location = location;}

	public String getRate() {return rate;}
	public void setRate(String rate) {
		if(rate.equals("1")) {
			this.rate = "pass";
		}else {
			this.rate = "fail";
		}
	}

	
	
}
