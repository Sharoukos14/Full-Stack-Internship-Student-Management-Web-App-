package App.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class OpenPosition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	private String companyUsername;
	private String companyName;
	private String startDate;
	private String endDate;
	private String jobDescription;
	private String requiredSkills;
	private String topicsOfInterest;
	private String Location;
	
	public OpenPosition(String username) {
		this.setCompanyUsername(username);
	}
	
	public OpenPosition() {
		
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
	
	public long getId() {return id;}

	public String getLocation() {return Location;}
	public void setLocation(String location) {Location = location;}

	public String getCompanyName() {return companyName;}

	public void setCompanyName(String companyName) {this.companyName = companyName;}
}
