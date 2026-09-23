package App.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import App.domain.Company;
import App.domain.MyUserDetails;
import App.domain.Professor;
import App.domain.Role;
import App.domain.Student;
import App.domain.User;
import App.repo.CompanyRepository;
import App.repo.ProfessorRepository;
import App.repo.StudentRepository;
import App.repo.UserRepository;

@Service
public class UserService implements UserDetailsService{
	@Autowired
    private PasswordEncoder encoder;
	@Autowired
	private UserRepository repo;
	@Autowired 
	private CompanyRepository compRepo;
	
	@Autowired
	private StudentRepository studRepo;
	
	@Autowired
	private ProfessorRepository professorRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User user = repo.findByUsername(username) ;
		
		if (user == null){
			System.out .println("User Not Found");
			throw new UsernameNotFoundException("user not found") ;
		}
			
		return new MyUserDetails(user);
	}
	
	public boolean isAlreadyUser(String username) {
		if(repo.findByUsername(username) == null) {
			return false;
		}else {
			return true;
		}
	}
	
	public void register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		save(user);
	}
	
	public void save(User user) {
        repo.save(user);
        if(user.getRole() == Role.ROLE_COMPANY) {
			Company company = new Company(user.getUsername());
			compRepo.save(company);
		}else if(user.getRole() == Role.ROLE_STUDENT){
			Student student = new Student(user.getUsername());
			studRepo.save(student);
		}else if(user.getRole() == Role.ROLE_PROFESSOR){
			Professor professor = new Professor(user.getUsername());
			professorRepo.save(professor);
		}else if(user.getRole() == Role.ROLE_COMMISSIONER){
			
		}
			
    }
	
	public Role getLoggedRole() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User user = repo.findByUsername(username);
		Role ret = user.getRole();
		return ret;
	}
	
	public String getLoggedUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User user = repo.findByUsername(username);
		String ret = user.getUsername();
		return ret;
	}

	

	

	
	

		
}