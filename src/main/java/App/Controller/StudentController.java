package App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import App.Service.StudentService;
import App.Service.UserService;

@RequestMapping("/student")
@Controller
public class StudentController {
	@Autowired
	UserService	userService;
	
	@Autowired
	StudentService	studentService;
	
	@GetMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("studentName",studentService.getStudentName());
		model.addAttribute("uniId",studentService.getStudentUniId());
		model.addAttribute("interests",studentService.getStudentInterests());
		model.addAttribute("skills",studentService.getStudentSkills());
		model.addAttribute("location",studentService.getStudentPrefLocation());
		return "student-profile";
	}
	
	@GetMapping("/profile/edit")
	public String editProfile(Model model) {
		model.addAttribute("username", userService.getLoggedUsername());
        return "student-profile-edit"; 
    }
	
	@PostMapping("/profile/edit")
	public String updateProfile(@RequestParam String studentName,@RequestParam String uniId,@RequestParam String interests,@RequestParam String skills,@RequestParam String prefLocation) {
		studentService.updateStudentProfile(studentName,uniId,interests,skills,prefLocation);
		return "redirect:/student/profile";
	}
	
	@GetMapping("/logbook")
	public String logbook(Model model) {
		model.addAttribute("username", userService.getLoggedUsername());
		model.addAttribute("logbook", studentService.getLogbook());
		return "student-logbook";
	}
	
	@GetMapping("/logbook/add")
	public String addLogbook(Model model) {
		model.addAttribute("username", userService.getLoggedUsername());
        return "student-logbook-add"; 
    }
	
	@PostMapping("/logbook/add")
	public String updatelogbook(@RequestParam String adder, Model model) {
		studentService.updateStudentLogbook(adder);
		model.addAttribute("username", userService.getLoggedUsername());
		return "redirect:/student/logbook";
	}
	
	@GetMapping("/apply")
	public String apply(Model model) {
		studentService.applyForTraineeship();
		model.addAttribute("username", userService.getLoggedUsername());
		return "student";
	}
}
