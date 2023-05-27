package com.SpringDemo.DMS.Controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.SpringDemo.DMS.Model.PostProjects;
import com.SpringDemo.DMS.Repository.PostProjectsRepository;


@RestController
public class PostProjectsController {
    @Autowired
    private PostProjectsRepository projectRepo;
    private String projectName;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userProfile.html");
        return modelAndView;
    }
    @GetMapping("/postProjects")
    public ModelAndView postProjects() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("post-projects.html");
        return modelAndView;
    }
    

    @PostMapping("/save")
    public ModelAndView postProjectsForm(PostProjects pps) {
        System.out.println(pps.toString());
        projectRepo.save(pps);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("post-projects.html"); // Redirect to the viewprojects endpoint
        return modelAndView;
    }
    
    @GetMapping("/viewProjects")
    public  ModelAndView viewProjects(Model model) {
        List<PostProjects> project = projectRepo.findAll();
       
            model.addAttribute("projects", project);
            System.out.println(project);

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("view-projects.html"); // Redirect to the viewprojects endpoint
            return modelAndView;
    }
    
    
    @GetMapping("/projectDetails")
    public ModelAndView getProjectDetails(@RequestParam("name") String projectName, Model model) 
    {
    	PostProjects project = projectRepo.findById(projectName).orElse(null);
    	String projDesc = project.getProjDesc();
	    model.addAttribute("projectName", projectName);
	   model.addAttribute("projDesc",projDesc);
	    System.out.println(project);
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("projDetails");
	    return modelAndView;
  
    }
    
    
    /*
    @PostMapping("/projectDetails")
    public void handleProjectName(@RequestBody Map<String, String> payload, HttpSession session) {
        projectName = payload.get("projectName");
        // Process the project name as needed
        System.out.println(projectName);
        session.setAttribute("projectName", projectName); // Store the project name in the session
        
    }

       @GetMapping("/projectDetails")
       public ModelAndView viewProjectDetails(Model model, HttpSession session) {
    	    // Retrieve the project name from the session
    	    projectName = (String) session.getAttribute("projectName");
    	    System.out.println("get mapping"+projectName);
    	    
    	    
    	    PostProjects project = projectRepo.findById(projectName).orElse(null);
    	    String projDesc = project.getProjDesc();
    	    model.addAttribute("projectName", projectName);
    	   model.addAttribute("projDesc",projDesc);
    	    System.out.println(project);
    	    ModelAndView modelAndView = new ModelAndView();
    	    modelAndView.setViewName("projDetails");
    	    return modelAndView;
       }*/

        
    }
        


    
    
    
    
    

