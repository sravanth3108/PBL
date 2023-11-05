package com.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.Model.PostProjectsModel;
import com.project.Model.User;
import com.project.Repository.UserRepository;

@Controller
public class LoginController {
	private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	@GetMapping("/login")
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }
	
	@GetMapping("/bearcat")
    public ModelAndView showBearcatPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("bearcat.html"); 
        return modelAndView;
	}
        
	@GetMapping("/admin")
    public ModelAndView showAdminPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin.html"); 
        return modelAndView;
    }
	
    @PostMapping("/login")
    public String loginUser(User user, Model model) {
        User existingUser = userRepository.findById(user.getMail()).orElse(null);
        String url = "redirect:/userProfile?mail=" + user.getMail();
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
  
            return url; // 
        } else {
            model.addAttribute("error", "Invalid email or password. Please try again.");
            return "login";
        }
    }
}

	
	


