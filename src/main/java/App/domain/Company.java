package App.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	private String username;
	private String companyName = "<Empty>";
	private String companyLocation = "<Empty>";
	
	public Company() {
		
	}
	
	public Company(String username) {
		this.username = username;
	}
	
	public String getUser() {return username;}
	public void setUser(String username) {this.username = username;}
	
	public String getName() {return companyName;}
	public void setName(String name) {this.companyName = name;}
	
	public String getLocation() {return companyLocation;}
	public void setLocation(String location) {this.companyLocation = location;}
}
