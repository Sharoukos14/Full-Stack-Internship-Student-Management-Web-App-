package App.Controller;

import App.domain.Role;
import App.domain.User;
import App.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register"; 
    }
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
       
    	if (userService.isAlreadyUser(user.getUsername())) {
    		model.addAttribute("error", "Το όνομα χρήστη υπάρχει ήδη. Δοκιμάστε άλλο.");
    		return "register";
    	}

       	userService.register(user);
      	return "reg-success";
    }

    @GetMapping("/success")
	public String mpravo() {
		Role ret = userService.getLoggedRole();
		if(ret == Role.ROLE_COMPANY) {
			return "redirect:/company";
		}else if(ret == Role.ROLE_STUDENT){
			return "redirect:/student";
		}else if(ret == Role.ROLE_PROFESSOR){
			return "redirect:/professor";
		}else if(ret == Role.ROLE_COMMISSIONER){
			return "redirect:/commissioner";
		}else {
			return null;
		}

	}
	
	@GetMapping("/student")
	public String makeStudent(Model model) {
        model.addAttribute("username", userService.getLoggedUsername());
		return "student";
	}
	
	@GetMapping("/company")
	public String makeCompany(Model model) {
        model.addAttribute("username", userService.getLoggedUsername());
		return "company";
	}
	
	@GetMapping("/professor")
	public String makeProfessor(Model model) {
        model.addAttribute("username", userService.getLoggedUsername());
		return "professor";
	}
	
	@GetMapping("/commissioner")
	public String makeCommissioner(Model model) {
        model.addAttribute("username", userService.getLoggedUsername());
		return "commissioner";
	}
    
}
