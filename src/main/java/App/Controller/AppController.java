package App.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AppController {

	@GetMapping("/")
	public String toLog(){
		
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String log(){
		return "login";
	}
	
	 
}
