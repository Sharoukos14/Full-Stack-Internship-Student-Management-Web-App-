package App.domain;



import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	private String username;
	private String studentName = "<Empty>";
	private String studentUniId = "<Empty>";
	private String studentInterests= "<Empty>";
	private String studentSkills = "<Empty>";
	private String studentPrefLocation = "<Empty>";
	private int isApplied = 0;
	private ArrayList<String> logbook = new ArrayList<String>();
	
	public Student() {
		
	}
	
	public Student(String username) {
		this.username = username;
	}

	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}

	public String getStudentName() {return studentName;}
	public void setStudentName(String studentName) {this.studentName = studentName;}

	public String getStudentPrefLocation() {return studentPrefLocation;}
	public void setStudentPrefLocation(String studentPrefLocation) {this.studentPrefLocation = studentPrefLocation;}

	public String getStudentUniId() {return studentUniId;}
	public void setStudentUniId(String studentUniId) {this.studentUniId = studentUniId;}

	public String getStudentInterests() {return studentInterests;}
	public void setStudentInterests(String studentInterests) {this.studentInterests = studentInterests;}

	public String getStudentSkills() {return studentSkills;}
	public void setStudentSkills(String studentSkills) {this.studentSkills = studentSkills;}

	public int isApplied() {return isApplied;}
	public void setApplied(int isApplied) {this.isApplied = isApplied;}

	public ArrayList<String> getLogbook() {return logbook;}
	public void addLogbook(String str) {this.logbook.add(str);}

	
	
}
