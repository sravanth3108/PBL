package com.project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// ...


import com.project.EmailService;
import com.project.Model.Bearcat;
import com.project.Model.Comments;
import com.project.Model.PostProjectsModel;
import com.project.Model.Requests;
import com.project.Model.User;
import com.project.Repository.BearcatRepository;
import com.project.Repository.CommentRepository;
import com.project.Repository.PostProjectsRepository;
import com.project.Repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PostProjects {
	
	  private EmailService emailService;
	  String subject;
	  User user;
	  Bearcat bearcat;

	    private PostProjectsRepository projectRepo;
	    private CommentRepository commentRepo;
	    private UserRepository userRepo;

	    private BearcatRepository bearcatRepo;

	@Autowired
    public PostProjects(UserRepository userRepo, PostProjectsRepository projectRepo,BearcatRepository bearcatRepo,CommentRepository commentRepo, EmailService emailService) {
        this.userRepo = userRepo;
        this.projectRepo = projectRepo;
        this.bearcatRepo= bearcatRepo;
        this.emailService= emailService;
        this.commentRepo = commentRepo;
    }
    private String projectName;
	 int count =0;

	    @GetMapping("/userProfile")
	    public ModelAndView getUserProfile(@RequestParam("mail") String email,HttpSession session, Model model)  {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userProfile.html");
        session.setAttribute("email", email);
        String username;
        List<PostProjectsModel> approved = projectRepo.findByApproved('y');
        List<PostProjectsModel> rejected = projectRepo.findByApproved('n');
        List<PostProjectsModel> pending = projectRepo.findByApproved('m');
        int approvedCount = approved.size();
        int rejectedCount = rejected.size();
        int pendingCount = pending.size();        
        
        user = userRepo.findById(email).orElse(null);
        String profilePicURL = user.getProfilePictureURL();
    	bearcat = bearcatRepo.findById(email).orElse(null);
        if(user==null)
        {
            username = bearcat.getFname() + " " +bearcat.getLname();
            profilePicURL = bearcat.getProfilePictureURL();
                       
        }
        else
        {

            username = user.getFname() + " " +user.getLname();
            profilePicURL = user.getProfilePictureURL();
            byte[] profilePicture = user.getProfilePicture();
            model.addAttribute("profilePicture", profilePicture != null ? Base64.getEncoder().encodeToString(profilePicture) : "");
        }
        
       System.out.print(username);
       model.addAttribute("email",email);
       model.addAttribute("username", username);
       model.addAttribute("approved", approvedCount);
       model.addAttribute("pending", pendingCount);
       model.addAttribute("rejected", rejectedCount);       
       model.addAttribute("count", rejectedCount);
       model.addAttribute("profilePictureURL",profilePicURL );
        
        List<PostProjectsModel> project = projectRepo.findByUserMail(email);
        model.addAttribute("projects", project);
        model.addAttribute("count", project.size());
         System.out.println(project);
        return modelAndView;
    }

	    

	    @PostMapping("/uploadProfilePicture")
	    public String uploadProfilePicture(HttpSession session, @RequestParam("profilePicture") MultipartFile file) throws IOException {

	        String mail = (String) session.getAttribute("email");
	       
	            // Check if the uploaded file is not empty
	            if (!file.isEmpty()) {
	                // Convert the MultipartFile to a byte array
	                byte[] fileBytes = file.getBytes();

	                // Update the user's profile picture in the database
	                User user = userRepo.findById(mail).orElse(null);

	                if (user != null) {
	                    // Update the profile picture
	                    user.setProfilePicture(fileBytes);

	                    // Save the updated user to the database
	                    userRepo.save(user);
	                    
	                }
	            }
	            
                return "redirect:/userProfile?mail=" + mail;
                
	    }

	
	    @RequestMapping("/postProjects")
	    public ModelAndView postProjects(HttpSession session, Model model) {	    	

	    	String email = (String) session.getAttribute("email");
		    model.addAttribute("email",email);
	        ModelAndView modelAndView = new ModelAndView();	         
            modelAndView.setViewName("post-projects.html");
            return modelAndView;
    }
	
	@GetMapping("/searchProjects")

    public String searchProject(@RequestParam("keywords") String projectName, Model model) 
    
    
	{
		String str ="";
	        for(PostProjectsModel pps: projectRepo.findByKeywordsContaining(projectName))
	        {
	        	model.addAttribute("projects",pps);
	        }
	        for(PostProjectsModel pps: projectRepo.findByNameContaining(projectName))
	        {
	        	model.addAttribute("projects",pps);
	        }
	        return "view-projects";
	        
		/*
    	List<PostProjectsModel> projects = projectRepo.findAll();
    	List<PostProjectsModel> projectsFoundList = null;
    	List<String> projectsFound = null;
    	for(PostProjectsModel pps : projects)
    	{
    		String keywords = pps.getKeywords(); 
    		
    		if(keywords.contains(projectName))
    				{
    			
    					projectsFound.add(projectName);
    					
    				}
    	}
    	
    	for(String s: projectsFound )
    	{

        	projectsFoundList = projectRepo.findBy
        	
    	}
        model.addAttribute("projects", projectsFoundList);   
        System.out.println(projectsFoundList);
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("post-projects.html");
        return modelAndView;*/
		
		
	}
    
	/*
	@PostMapping("/save")
	public ModelAndView postProjectsForm(@ModelAttribute PostProjectsModel pps) {
	    System.out.println(pps);
	    // Set any other attributes in the model as needed	    
	    projectRepo.save(pps);
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("redirect:/viewprojects"); // Redirect to the endpoint where you view projects
	    return modelAndView;
	}*/
	
	@PostMapping("/postProjects")
	public ModelAndView postProjectsForm(@ModelAttribute PostProjectsModel pps,HttpSession session, Model model) {
	    System.out.println(pps.toString());

    	String email = (String) session.getAttribute("email");
    	 User user= userRepo.findById(email).orElse(null);
    	 String userName = user.getUsrName();
    	System.out.println("email in post"+email);
	    // Set any other attributes in the model as needed
	    pps.setUserMail(email);
	    pps.setUsrName(userName);
	    
	    pps.setApproved('m');
	    projectRepo.save(pps);
	    model.addAttribute("email",email);
	    
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("redirect:/viewProjects"); // Redirect to the endpoint where you view projects
	    return modelAndView;
	}
	
	@GetMapping("/")
	public String projects(Model model) throws java.text.ParseException {
		    List<PostProjectsModel> projects = projectRepo.findAll();
		    List<PostProjectsModel> expiredProjects = new ArrayList<>(); // Create a list to store expired projects

		    SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    SimpleDateFormat outputDateFormat = new SimpleDateFormat("MM/dd/yyyy");

		    for (PostProjectsModel pps : projects) {
		        String startDateString = pps.getExpiryDate();
		        String projType=pps.getProjType();
		        try {
		            Date expDate = inputDateFormat.parse(startDateString);

		            if (expDate.after(new Date()) && projType.equalsIgnoreCase("public")) {
		                pps.setExpiryDate(outputDateFormat.format(expDate)); // Update the expiry date format if needed
		                expiredProjects.add(pps); // Add expired project to the list
		            }
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
		        System.out.print(pps);
		    }

		    model.addAttribute("projects", expiredProjects); // Add the list of expired projects to the model
		    return "a.html";
		}


	
	@GetMapping("/viewProjects")
	public String viewProjects(Model model, HttpSession session) throws java.text.ParseException {
		    List<PostProjectsModel> projects = projectRepo.findAll();
		    List<PostProjectsModel> expiredProjects = new ArrayList<>(); // Create a list to store expired projects

		    SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    SimpleDateFormat outputDateFormat = new SimpleDateFormat("MM/dd/yyyy");

		    for (PostProjectsModel pps : projects) {
		        String startDateString = pps.getExpiryDate();
		        try {
		            Date expDate = inputDateFormat.parse(startDateString);

		            if (expDate.after(new Date())) {
		                pps.setExpiryDate(outputDateFormat.format(expDate)); // Update the expiry date format if needed
		                expiredProjects.add(pps); // Add expired project to the list
		            }
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
		    }

		    model.addAttribute("projects", expiredProjects); // Add the list of expired projects to the model

		    model.addAttribute("email",(String)session.getAttribute("email"));
		    return "view-projects";
		}

	@GetMapping("/viewProjects/expired")
	public String viewProjectsExp(Model model, HttpSession session) throws java.text.ParseException {

	    List<PostProjectsModel> projects = projectRepo.findAll();
	    List<PostProjectsModel> expiredProjects = new ArrayList<>(); // Create a list to store expired projects

	    SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat outputDateFormat = new SimpleDateFormat("MM/dd/yyyy");

	    for (PostProjectsModel pps : projects) {
	        String startDateString = pps.getExpiryDate();
	        try {
	            Date expDate = inputDateFormat.parse(startDateString);

	            if (expDate.before(new Date())) {
	                pps.setExpiryDate(outputDateFormat.format(expDate)); // Update the expiry date format if needed
	                expiredProjects.add(pps); // Add expired project to the list
	            }
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        System.out.print(pps);
	    }

	    model.addAttribute("projects", expiredProjects); 

	    model.addAttribute("email",(String)session.getAttribute("email"));// Add the list of expired projects to the model
	    return "viewExpProjects";
	}


	@GetMapping("/projectDetails")
	public ModelAndView getProjectDetails(@RequestParam("name") String projectName, Model model, HttpSession session) {
	    PostProjectsModel project = projectRepo.findById(projectName).orElse(null);
	    
	    if (project != null) {
	        String projDescfull = project.getProjDescfull();
	        String projDesc = project.getProjDesc();
	        String phone = project.getPhone();
	        String email = project.getEmail();
	        String projReq = project.getProjReq();
	        String projType = project.getProjType();
	        int projectExp = project.getProjExp();
	        
	        // Add project details to the model
	        model.addAttribute("projDescFull", projDescfull);
	        model.addAttribute("projDesc", projDesc);
	        model.addAttribute("phone", phone);
	        model.addAttribute("email", email);
	        model.addAttribute("projReq", projReq);
	        model.addAttribute("projType", projType);
	        model.addAttribute("projectExp", projectExp);
	        model.addAttribute("projectName", projectName);

	    	String emails = (String) session.getAttribute("email");
	    	User user = userRepo.findById(emails).orElse(null);

	    	Bearcat bearcat = bearcatRepo.findById(emails).orElse(null);
	    	if (user != null) {
	    	    System.out.println(user.getUsrName());
	    	    model.addAttribute("username", user.getUsrName());
	    	}
	    	else if(bearcat!=null)
	    	{

	    	    model.addAttribute("username", bearcat.getUsrName());
	    		
	    	}
	    		
	        
	        // Retrieve comments based on the project name
	    	List<Comments> comments = commentRepo.findByProjectId(projectName);

	        Collections.reverse(comments);
	    	System.out.println("Retrieved comments for project: " + projectName);
	    	System.out.println("Number of comments: " + comments.size());
	        model.addAttribute("comments", comments);
	        model.addAttribute("email", emails);
	        System.out.println(comments);
	        // Set the view name to "projDetails"
		    ModelAndView modelAndView = new ModelAndView();
		    modelAndView.setViewName("projDetails");
		    return modelAndView;
	        
	    } else {
	        // Handle case when the project with given name does not exist
	        // You can redirect or show an error message
	        // For example, redirect to an error page
	        return new ModelAndView("redirect:/error"); // Assuming you have an error page mapped to "/error"
	    }
	}

	 
	 @GetMapping("/editProjects")
	    public ModelAndView editProject(@RequestParam("name") String projectName, HttpSession session, Model model) 
	    {

			PostProjectsModel project = projectRepo.findById(projectName).orElse(null);
	    	String projDescfull = project.getProjDescfull();
	    	String projDesc = project.getProjDesc();
	    	String phone= project.getPhone();
	    	String email = project.getEmail();
	    	String projReq = project.getProjReq();
	    	String projType = project.getProjType();
	    	int projectExp = project.getProjExp();
	    	//System.out.println("/n/nproject - na+projectName);
		    model.addAttribute("projectName", projectName);
		    model.addAttribute("projDescfull",projDescfull);
		    model.addAttribute("projectDesc",projDesc);
		    model.addAttribute("projReq",projReq);
		    model.addAttribute("phone",phone);
		    model.addAttribute("email",email);
		    model.addAttribute("projType", projType);
		    model.addAttribute("projectExp",projectExp);
		    model.addAttribute("email",(String)session.getAttribute("email"));
		    //model.addAllAttributes(project);
  		    System.out.println(model);
		    ModelAndView modelAndView = new ModelAndView();
		    modelAndView.setViewName("editProjects");
		    return modelAndView;
	  
	    }
	 @PostMapping("/updateProject")
	 public ModelAndView updateProject(@ModelAttribute PostProjectsModel updatedProject, @RequestParam("name") String projectName) {
	     PostProjectsModel project = projectRepo.findById(projectName).orElse(null);
	     project.setProjReq(updatedProject.getProjReq());
	     project.setProjDesc(updatedProject.getProjDesc());
	     project.setProjDescfull(updatedProject.getProjDescfull());
	     project.setPhone(updatedProject.getPhone());
	     project.setEmail(updatedProject.getEmail());
	     project.setProjType(updatedProject.getProjType());
	     project.setProjExp(updatedProject.getProjExp());
	     project.setProjName(updatedProject.getProjName());
	
	     projectRepo.save(project);

	     return new ModelAndView("redirect:/");
	 }
	 


	 @DeleteMapping("/deleteProject")
	 public String deleteProject(@RequestParam("name") String projectName) {
	        PostProjectsModel project = projectRepo.findById(projectName).orElse(null);
	        if (project != null) {
	            projectRepo.delete(project);
	            return "redirect:/?successMessage=Project successfully deleted!";
	        } else {
	            return "redirect:/?errorMessage=Project not found!";
	        }
	    }

	 /*
	 @GetMapping("/deleteProject")
	 public ModelAndView deleteProject(@RequestParam("name") String projectName) {
	     PostProjectsModel project = projectRepo.findById(projectName).orElse(null);	     
	     if (project != null) {
	         projectRepo.delete(project);
	         ModelAndView modelAndView = new ModelAndView("redirect:/");
	         modelAndView.addObject("successMessage", "Project successfully deleted!");
	         return modelAndView;
	     } else {
	         ModelAndView modelAndView = new ModelAndView("redirect:/");
	         modelAndView.addObject("errorMessage", "Project not found!");
	         return modelAndView;
	     }
	 }*/

	 
	 @GetMapping("/flagProject")
	    public ModelAndView flagProject(@RequestParam("name") String projectName, Model model) 
	    {
	     PostProjectsModel project = projectRepo.findById(projectName).orElse(null);
	     project.setFlags(project.getFlags()+1);
	     projectRepo.save(project);
	     int flagCount = project.getFlags();
	     model.addAttribute("flag",flagCount);
	     emailService.sendEmail("sravanthreddy.pullamgari@gmail.com", "A project has been flagged", projectName+" has been flagged and request your action on the issue");

	   /*  SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo("bellamkondalohith@gmail.com");
	        message.setSubject("");
	        message.setText(project.getProjName()+"has been flagged ");
	        javaMailSender.send(message);*/
	     return new ModelAndView("redirect:/viewProjects");
	    }
	 

	 @GetMapping("/flagComments")
	    public ModelAndView flagComment(@RequestParam("comment") String commentId, Model model, HttpSession httpsession) 
	    {
		 
		 Comments comment = commentRepo.findById(commentId).orElse(null);
		 if(comment!=null)
		 {
			 comment.setFlags(comment.getFlags()+1);
			 commentRepo.save(comment);
		 }
		 emailService.sendEmail("sravanthreddy.pullamgari@gmail.com", "A project has been flagged", projectName+" has been flagged and request your action on the issue");
		 String url =  "/projectDetails?" + httpsession.getAttribute(projectName);
				     return new ModelAndView("redirect:/"+url);
	    
	    }
	 
	 /*
	  * @PostMapping("/saveRequests")
	 public ModelAndView postProjectsForm(@RequestParam("contactName") String contactName, @RequestParam("contactEmail") String contactEmail,PostProjectsModel pps) {
	     // Your method logic
	 

	        Map<String, String> requests = new HashMap<>();
	        requests.put(contactName, contactEmail); // Add the multivalued attribute

	        // Set any other attributes in the model as needed
	        PostProjectsModel project = new PostProjectsModel(pps.getProjName(), pps.getProjDesc(), pps.getProjDescfull(), pps.getProjReq(), pps.getEmail(), pps.getPhone(), pps.getProjType(), pps.getProjExp(), requests);
	        projectRepo.save(project);

	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("redirect:/viewProjects"); // Redirect to the endpoint where you view projects
	        return modelAndView;
	    }
     */
}