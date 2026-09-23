package App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import App.Service.CommissionerService;
import App.Service.StudentService;
import App.Service.UserService;

@RequestMapping("/commissioner")
@Controller
public class CommissionerController {
	@Autowired
	UserService	userService;
	
	@Autowired
	StudentService	studentService;
	
	@Autowired 
	CommissionerService commissionerService;
	
	
	@GetMapping("/appliedStudents")
	public String profile(Model model) {
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("students", commissionerService.getAppliedStudents());
		return "commissioner-applied-students";
	}
	
	@GetMapping("/traineeshipsInProgress")
	public String listOfPositions(Model model) {
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("filledPositions", commissionerService.getAllFilledPositions());
		return "commissioner-traineeships";
	}

	@PostMapping("/traineeshipsInProgress/view/{id}")
	public String viewTraineeShip(Model model,@PathVariable Long id) {
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("filledPosition", commissionerService.getFilledPositionById(id));
		return "commissioner-traineeship-viewer";
	}
	
	@PostMapping("/traineeshipsInProgress/allocateInterests/{id}")
	public String allocateInterests(Model model,@PathVariable Long id) {
		model.addAttribute("username", userService.getLoggedUsername());
		commissionerService.allocateInterests(id);
		model.addAttribute("filledPosition", commissionerService.getFilledPositionById(id));
		return "redirect:/commissioner/traineeshipsInProgress";
	}
	
	@PostMapping("/traineeshipsInProgress/allocateLoad/{id}")
	public String allocateLoad(Model model,@PathVariable Long id) {
		model.addAttribute("username", userService.getLoggedUsername());
		commissionerService.allocateLoad(id);
		model.addAttribute("filledPosition", commissionerService.getFilledPositionById(id));
		return "redirect:/commissioner/traineeshipsInProgress";
	}
	
	@PostMapping("/traineeshipsInProgress/rate/{id}")
	public String finishTraineeship(Model model,@PathVariable Long id,@RequestParam String rate) {
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("filledPosition", commissionerService.rateTraineeship(id,rate));
		commissionerService.deleteById(id);
		return "commissioner-traineeship-viewer";
	}
	
	
	@PostMapping("/select/interests/{id}")
	public String selectStudentInterests(@PathVariable String id,Model model) {
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("student", studentService.getStudent(id));
		model.addAttribute("positions", commissionerService.getPositionsBasedInterests(id));
		
		return "commissioner-student-selection";
	}
	
	@PostMapping("/select/location/{id}")
	public String selectStudentLocation(@PathVariable String id,Model model) {
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("student", studentService.getStudent(id));
		model.addAttribute("positions", commissionerService.getPositionsBasedLocation(id));
		
		return "commissioner-student-selection";
	}
	
	@PostMapping("/select/both/{id}")
	public String selectStudentBoth(@PathVariable String id,Model model) {
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("student", studentService.getStudent(id));
		model.addAttribute("positions", commissionerService.getPositionsBasedBoth(id));
		
		return "commissioner-student-selection";
	}
	
	@PostMapping("/select/assign/{id}")
	public String assignStudent(@PathVariable Long id,@RequestParam String username,Model model) {
		model.addAttribute("username", userService.getLoggedUsername());
		commissionerService.assignStudent(username,id);
		
		return "redirect:/commissioner/traineeshipsInProgress";
	}
	
	
	
}