package com.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.Model.PostProjectsModel;
import com.project.Model.User;

import com.project.Model.Bearcat;
import com.project.Repository.BearcatRepository;
import com.project.Repository.UserRepository;

@Controller
public class LoginController {
	private final UserRepository userRepository;

	private final BearcatRepository bearcatRepository;

    @Autowired
    public LoginController(UserRepository userRepository, BearcatRepository bearcatRepository) {
        this.userRepository = userRepository;
		this.bearcatRepository = bearcatRepository;
    }
	
	@GetMapping("/login")
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }
	/*
	@GetMapping("/bearcat")
    public ModelAndView showBearcatPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("bearcat.html"); 
        return modelAndView;
	}
	

    @PostMapping("/bearcat")
    public String loginBearcat(Bearcat bearcat, Model model) {
        Bearcat existingUser = bearcatRepository.findById(bearcat.getMail()).orElse(null);
        String url = "redirect:/userProfile?mail=" + bearcat.getMail();
        System.out.println("called");
        if (existingUser != null && existingUser.getPassword().equals(bearcat.getPassword())) {
  System.out.println("success");
            return url; // 
        } else {
            model.addAttribute("error", "Invalid email or password. Please try again.");
            return "redirect:/login";

        }
    }*/
	  @RequestMapping("/bearcat")
	    public String showLoginPage(Model model) {
	        model.addAttribute("bearcat", new Bearcat());
	        return "bearcat"; // Assuming your login page template is named "login.html"
	    }

	    @PostMapping("/bearcat")
	    public String loginBearcat(Bearcat bearcat, Model model) {
	        Bearcat existingUser = bearcatRepository.findByMail(bearcat.getMail());
	        String url = "redirect:/userProfile?mail=" + bearcat.getMail();

	        if (existingUser != null && existingUser.getPassword().equals(bearcat.getPassword())) {
	            return url; // Redirect to user profile page if authentication is successful
	        } else {
	            model.addAttribute("error", "Invalid email or password. Please try again.");
	            return "redirect:/login"; // Redirect back to the login page with an error message
	        }
	    }
	    

        
	@GetMapping("/admin")
    public ModelAndView showAdminPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin.html"); 
        return modelAndView;
    }
	
    @PostMapping("/verifylogin")
    public String loginUser(User user, Model model) {
        User existingUser = userRepository.findById(user.getMail()).orElse(null);
        String url = "redirect:/userProfile?mail=" + user.getMail();
        System.out.println("called");
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
  System.out.println("success");
            return url; // 
        } else {
            model.addAttribute("error", "Invalid email or password. Please try again.");
            return "redirect:/login";

        }
    }
}

	
	


