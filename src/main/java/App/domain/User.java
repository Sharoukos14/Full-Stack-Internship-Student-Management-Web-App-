package App.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	private String username;
	private String password;
	private Role role;
	
	public User() {}
	
	//Getters
	public String getUsername() {return this.username;}
	public String getPassword() {return this.password;}
	public Role getRole(){return this.role;}
	//Setters
	public void setUsername(String t) {this.username = t;}
	public void setPassword(String t) {this.password = t;}
	public void setRole(Role t) {this.role = t;}
}
