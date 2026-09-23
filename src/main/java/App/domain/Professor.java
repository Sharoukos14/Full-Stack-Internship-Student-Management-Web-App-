package App.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Professor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	private String username;
	private String professorName = "<Empty>";
	private String professorInterests = "<Empty>";
	private int professorLoad = 0;
	
	public Professor() {
		
	}
	
	public Professor(String username) {
		this.username = username;
	}
	
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	
	public String getProfessorName() {return professorName;}
	public void setProfessorName(String professorName) {this.professorName = professorName;}
	
	public String getProfessorInterests() {return professorInterests;}
	public void setProfessorInterests(String professorInterests) {this.professorInterests = professorInterests;}

	public int getProfessorLoad() {return professorLoad;}
	public void setProfessorLoad(int professorLoad) {this.professorLoad = professorLoad;}
}