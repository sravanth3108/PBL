package com.project.Controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.EmailService;
import com.project.Model.PostProjectsModel;
import com.project.Model.Requests;
import com.project.Repository.PostProjectsRepository;

@RestController
	 public class RequestsController {

	     @Autowired
	     private PostProjectsRepository projectRepo;
	     int count =0;
		  private EmailService emailService;
		  String subject;


			@Autowired
			    public RequestsController(EmailService emailService) {
			        this.emailService = emailService;
			    }

	     @GetMapping("/getRequests")
	     public List<Requests> getRequests(@RequestParam("projName") String projName) throws Exception {
	         // Retrieve the project from the database based on the projName
	         PostProjectsModel project = projectRepo.findById(projName).orElse(null);

	         // Return the list of requests for the specified project
	         if (project != null) {
	             return project.getRequests();
	         } else {
	             // Handle the case when the project is not found
	             throw new Exception("Project not found with name: " + projName);
	         }
	     }
	 

	 
	 @RequestMapping("/saveRequests")
	    public ModelAndView saveRequests(
	            @RequestParam("contactName") String contactName,
	            @RequestParam("contactEmail") String contactEmail,
	            @RequestParam("projName") String projName) {
		 
		 PostProjectsModel project = projectRepo.findById(projName).orElse(null);
		 subject =  "PBL  ----   You have a request for your project";
		 Requests req = new Requests();
		 req.setContactEmail(contactEmail);
		 req.setContactName(contactName);
		 List<Requests> reqs= new ArrayList<>();
		 reqs.add(req);
		 count = project.getRequestCount();
		 
		 if(project.getRequests()==null)
		 {
			 project.setRequests(reqs);
			 count= count+1;
		 }
		 else
		 {
		 project.getRequests().add(req);
		 count=count+1;
		 }
		 project.setRequestCount(count);
		 
		 projectRepo.save(project);
		 String body =  "You have a request for your project\t"+projName+"\n The Name of the requester is :\t" +contactName+"\nThe email id of the requester is :\t"+contactEmail;
		 emailService.sendEmail("sravanthreddy.pullamgari@gmail.com", subject, body);


		    ModelAndView modelAndView = new ModelAndView();
		    modelAndView.setViewName("redirect:/viewProjects"); // Redirect to the endpoint where you view projects
		    return modelAndView;
	     
	    }
	 
	// Example method in your controller or service to get request count for each project
	 public int getRequestCountsForProjects(String projName) {
	     PostProjectsModel projects = projectRepo.findById(projName).orElse(null) ;// Assuming you have a method to retrieve projects from the database

	         List<Requests> countOfRequests =projects.getRequests(); // Assuming you have a method to get request count by project name
	         int count = countOfRequests.size();
	         
	    
	     return count;
	 }

	 
	 

}
