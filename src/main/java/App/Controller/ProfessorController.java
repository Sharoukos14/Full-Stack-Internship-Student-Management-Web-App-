package App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import App.Service.ProfessorService;
import App.Service.UserService;

@RequestMapping("/professor")
@Controller
public class ProfessorController {
	@Autowired
	UserService	userService;
	
	@Autowired
	ProfessorService professorService;
	
	@GetMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("professorName",professorService.getProfessorName());
		model.addAttribute("professorInterests",professorService.getProfessorInterests());
		return "professor-profile";
	}
	
	@GetMapping("/profile/edit")
	public String editPorfile(Model	model) {
		model.addAttribute("username", userService.getLoggedUsername());
	    return "professor-profile-edit"; 
	    }
		
	@PostMapping("/profile/edit")
	public String updateProfile(@RequestParam String professorName,@RequestParam String professorInterests) {
		professorService.updateProfessorProfile(professorName,professorInterests);
		return "redirect:/professor/profile";
	}
		
	
	@GetMapping("/myTraineeships")
	public String listOfPositions(Model model) {
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("traineeships", professorService.getProfessorPositions());
		return "professor-traineeships";
	}
	
	@PostMapping("/myTraineeships/rate/motivation/{id}")
	public String rateMotivation(@PathVariable Long id,@RequestParam String rate,Model model) {
		professorService.rateMotivationById(id,rate);
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("filledPositions",professorService.getProfessorPositions());
	    return "redirect:/professor/myTraineeships";
	}
	
	@PostMapping("/myTraineeships/rate/effectiveness/{id}")
	public String rateEffectiveness(@PathVariable Long id,@RequestParam String rate,Model model) {
		professorService.rateEffectivenessById(id,rate);
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("filledPositions",professorService.getProfessorPositions());
	    return "redirect:/professor/myTraineeships";
	}
	
	@PostMapping("/myTraineeships/rate/efficiency/{id}")
	public String rateEfficiency(@PathVariable Long id,@RequestParam String rate,Model model) {
		professorService.rateEfficiencyById(id,rate);
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("filledPositions",professorService.getProfessorPositions());
	    return "redirect:/professor/myTraineeships";
	}
	
	@PostMapping("/myTraineeships/rate/facilities/{id}")
	public String rateFacilities(@PathVariable Long id,@RequestParam String rate,Model model) {
		professorService.rateFacilitiesById(id,rate);
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("filledPositions",professorService.getProfessorPositions());
	    return "redirect:/professor/myTraineeships";
	}
	
	@PostMapping("/myTraineeships/rate/guidance/{id}")
	public String rateGuidance(@PathVariable Long id,@RequestParam String rate,Model model) {
		professorService.rateGuidanceById(id,rate);
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("filledPositions",professorService.getProfessorPositions());
	    return "redirect:/professor/myTraineeships";
	}
	
	
	
}