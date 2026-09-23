package App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import App.Service.CompanyService;
import App.Service.UserService;

@RequestMapping("/company")
@Controller
public class CompanyController {
	@Autowired
	UserService	userService;
	@Autowired 
	CompanyService companyService;
	
	@GetMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("companyName",companyService.getCompanyName());
		model.addAttribute("companyLocation",companyService.getCompanyLocation());
		return "company-profile";
	}
	
	@GetMapping("/profile/edit")
	public String editProfile(Model model) {
		model.addAttribute("username", userService.getLoggedUsername());
        return "company-profile-edit"; 
    }
	
	@PostMapping("/profile/edit")
	public String updateProfile(@RequestParam String companyName,@RequestParam String companyLocation) {
		companyService.updateCompanyProfile(companyName, companyLocation);
		return "redirect:/company/profile";
	}
	
	@GetMapping("/positions")
	public String listOfPositions(Model model) {
	    model.addAttribute("positions", companyService.allMyOpenPositions());
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("filledPositions", companyService.getFilledPositions());
		return "company-positions";
	}
	
	@PostMapping("/positions/delete/{id}")
	public String deletePosition(@PathVariable Long id,Model model) {
	    companyService.deleteById(id);
	    model.addAttribute("positions", companyService.allMyOpenPositions());
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("filledPositions", companyService.getFilledPositions());
	    return "redirect:/company/positions";
	}
	

	@PostMapping("/positions/rate/motivation/{id}")
	public String rateMotivation(@PathVariable Long id,@RequestParam String rate,Model model) {
		companyService.rateMotivationById(id,rate);
	    model.addAttribute("positions", companyService.allMyOpenPositions());
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("filledPositions", companyService.getFilledPositions());
	    return "redirect:/company/positions";
	}
	
	@PostMapping("/positions/rate/effectiveness/{id}")
	public String rateEffetiveness(@PathVariable Long id,@RequestParam String rate,Model model) {
		companyService.rateEffectivenessById(id,rate);
	    model.addAttribute("positions", companyService.allMyOpenPositions());
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("filledPositions", companyService.getFilledPositions());
	    return "redirect:/company/positions";
	}
	
	@PostMapping("/positions/rate/efficiency/{id}")
	public String rateEfficiency(@PathVariable Long id,@RequestParam String rate,Model model) {
		companyService.rateEfficiencyById(id,rate);
	    model.addAttribute("positions", companyService.allMyOpenPositions());
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("filledPositions", companyService.getFilledPositions());
	    return "redirect:/company/positions";
	}

	@GetMapping("/makePositions")
	public String makePositions(Model model) {
		model.addAttribute("username", userService.getLoggedUsername());
		return "company-position-maker";
	}
	
	@PostMapping("/makePositions")
	public String makePosition(@RequestParam String startDate,@RequestParam String endDate,
@RequestParam String jobDescription,@RequestParam String requiredSkills,@RequestParam String topicsOfInterest,Model model) {
		companyService.makePosition(startDate,endDate,jobDescription,requiredSkills,topicsOfInterest);
		model.addAttribute("username", userService.getLoggedUsername());
		return "redirect:/company/positions";
	}
	
	
}