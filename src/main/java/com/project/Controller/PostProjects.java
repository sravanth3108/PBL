package com.project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.project.Model.Subcomments;
import com.project.Model.User;
import com.project.Repository.BearcatRepository;
import com.project.Repository.CommentRepository;
import com.project.Repository.PostProjectsRepository;
import com.project.Repository.SubcommentRepository;
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
	    private SubcommentRepository subcommentRepo;

	    private BearcatRepository bearcatRepo;

	@Autowired
    public PostProjects(UserRepository userRepo, PostProjectsRepository projectRepo,BearcatRepository bearcatRepo,CommentRepository commentRepo, EmailService emailService, SubcommentRepository subcommentRepo) {
        this.userRepo = userRepo;
        this.projectRepo = projectRepo;
        this.bearcatRepo= bearcatRepo;
        this.emailService= emailService;
        this.commentRepo = commentRepo;
        this.subcommentRepo = subcommentRepo;
    }
    private String projectName;
	 int count =0;
	 @GetMapping("/userProfile")
	 public ModelAndView getUserProfile(@RequestParam("mail") String email, HttpSession session, Model model) {
	     ModelAndView modelAndView = new ModelAndView();
	     modelAndView.setViewName("userProfile.html");
	     session.setAttribute("email", email);
	     String username;

	     int approvedCount = 0;
	     int rejectedCount = 0;
	     int pendingCount = 0;

	     List<PostProjectsModel> approved = projectRepo.findByApproved('a');
	     List<PostProjectsModel> rejected = projectRepo.findByApproved('r');
	     List<PostProjectsModel> pending = projectRepo.findByApproved('m');

	     for (PostProjectsModel pps : approved) {
	         if (pps.getUserMail() != null && pps.getUserMail().trim().equals(email)) {
	             approvedCount++;
	         }
	     }
	     for (PostProjectsModel pps : rejected) {
	         if (pps.getUserMail() != null && pps.getUserMail().trim().equals(email)) {
	             rejectedCount++;
	         }
	     }
	     for (PostProjectsModel pps : pending) {
	         if (pps.getUserMail() != null && pps.getUserMail().trim().equals(email)) {
	             pendingCount++;
	         }
	     }

	     user = userRepo.findById(email).orElse(null);
	     String profilePicURL = null;
	     bearcat = bearcatRepo.findById(email).orElse(null);
	     if (user == null) {
	         username = bearcat.getFname() + " " + bearcat.getLname();
	         profilePicURL = bearcat.getProfilePictureURL();
	     } else {
	         username = user.getFname() + " " + user.getLname();
	         profilePicURL = user.getProfilePictureURL();
	         byte[] profilePicture = user.getProfilePicture();
	         model.addAttribute("profilePicture", profilePicture != null ? Base64.getEncoder().encodeToString(profilePicture) : "");
	     }

	     model.addAttribute("email", email);
	     model.addAttribute("username", username);
	     model.addAttribute("approved", approvedCount);
	     model.addAttribute("pending", pendingCount);
	     model.addAttribute("rejected", rejectedCount);
	     model.addAttribute("count", rejectedCount);
	     model.addAttribute("profilePictureURL", profilePicURL);

	     List<PostProjectsModel> project = projectRepo.findByUserMail(email);
	     model.addAttribute("projects", project);
	     model.addAttribute("count", project.size());
	     System.out.println(project);
	     return modelAndView;
	 }
	 
	 @GetMapping("/userProfilePage")
	 public ModelAndView getUserProfileFlag(@RequestParam("usermail") String email,  Model model) {
	     ModelAndView modelAndView = new ModelAndView();
	     modelAndView.setViewName("userProfilePage.html");
	     String username;

	     int approvedCount = 0;
	     int rejectedCount = 0;
	     int pendingCount = 0;

	     List<PostProjectsModel> approved = projectRepo.findByApproved('a');
	     List<PostProjectsModel> rejected = projectRepo.findByApproved('r');
	     List<PostProjectsModel> pending = projectRepo.findByApproved('m');

	     for (PostProjectsModel pps : approved) {
	         if (pps.getUserMail() != null && pps.getUserMail().trim().equals(email)) {
	             approvedCount++;
	         }
	     }
	     for (PostProjectsModel pps : rejected) {
	         if (pps.getUserMail() != null && pps.getUserMail().trim().equals(email)) {
	             rejectedCount++;
	         }
	     }
	     for (PostProjectsModel pps : pending) {
	         if (pps.getUserMail() != null && pps.getUserMail().trim().equals(email)) {
	             pendingCount++;
	         }
	     }

	     user = userRepo.findById(email).orElse(null);
	     String profilePicURL = null;
	     bearcat = bearcatRepo.findById(email).orElse(null);
	     if (user == null) {
	         username = bearcat.getFname() + " " + bearcat.getLname();
	         profilePicURL = bearcat.getProfilePictureURL();
	     } else {
	         username = user.getFname() + " " + user.getLname();
	         profilePicURL = user.getProfilePictureURL();
	         byte[] profilePicture = user.getProfilePicture();
	         model.addAttribute("profilePicture", profilePicture != null ? Base64.getEncoder().encodeToString(profilePicture) : "");
	     }

	     model.addAttribute("email", email);
	     model.addAttribute("username", username);
	     model.addAttribute("approved", approvedCount);
	     model.addAttribute("pending", pendingCount);
	     model.addAttribute("rejected", rejectedCount);
	     model.addAttribute("count", rejectedCount);
	     model.addAttribute("profilePictureURL", profilePicURL);

	     List<PostProjectsModel> project = projectRepo.findByUserMail(email);
	     model.addAttribute("projects", project);
	     model.addAttribute("count", project.size());
	     System.out.println(project);
	     return modelAndView;
	 }

	 
	 @GetMapping("/userProjects")
	 public ModelAndView getUserProjects( HttpSession session, Model model)
	 {

	    	String email = (String) session.getAttribute("email");
		 ModelAndView modelAndView = new ModelAndView();
	     modelAndView.setViewName("allprojects.html");
		 List<PostProjectsModel> project = projectRepo.findByUserMail(email);
	     model.addAttribute("projects", project);
	     model.addAttribute("count", project.size());
	     System.out.println(project);
	     return modelAndView;
	 
	 
	 }
	 @GetMapping("/approvedProjects")
	 public ModelAndView getapprovedProjects( HttpSession session, Model model)
	 {

	    	String email = (String) session.getAttribute("email");
		 ModelAndView modelAndView = new ModelAndView();
	     modelAndView.setViewName("allprojects.html");
		 List<PostProjectsModel> project = projectRepo.findByApproved('a');
		 List<PostProjectsModel> approvedProjects = new ArrayList<>();
		 for (PostProjectsModel pps : project) {
	         if (pps.getUserMail() != null && pps.getUserMail().trim().equals(email)) {
	             approvedProjects.add(pps);
	         }
	     }
	     model.addAttribute("projects", approvedProjects);
	     model.addAttribute("count", project.size());
	     System.out.println(project);
	     return modelAndView;
	 
	 
	 }
	 @GetMapping("/rejectedProjects")
	 public ModelAndView getRejectedProjects( HttpSession session, Model model)
	 {

	    	String email = (String) session.getAttribute("email");
		 ModelAndView modelAndView = new ModelAndView();
	     modelAndView.setViewName("allprojects.html");
	     List<PostProjectsModel> project = projectRepo.findByApproved('r');
		 List<PostProjectsModel> rejectedProjects = new ArrayList<>();
		 for (PostProjectsModel pps : project) {
	         if (pps.getUserMail() != null && pps.getUserMail().trim().equals(email)) {
	             rejectedProjects.add(pps);
	         }
	     }
	     model.addAttribute("projects", rejectedProjects);
	     model.addAttribute("count", project.size());
	     return modelAndView;
	 
	 
	 }
	   
	 @GetMapping("/pendingProjects")
	 public ModelAndView getPendingProjects( HttpSession session, Model model)
	 {

	    	String email = (String) session.getAttribute("email");
		 ModelAndView modelAndView = new ModelAndView();
	     modelAndView.setViewName("allprojects.html");

	     List<PostProjectsModel> project = projectRepo.findByApproved('m');
	     List<PostProjectsModel> pendingProjects = new ArrayList<>();
		 for (PostProjectsModel pps : project) {
	         if (pps.getUserMail() != null && pps.getUserMail().trim().equals(email)) {
	             pendingProjects.add(pps);
	         }
	     }
	     model.addAttribute("projects", pendingProjects);
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
	    public String searchProject(@RequestParam("keywords") String projectName, HttpSession session, Model model,
	                                @RequestParam(defaultValue = "0") int page,
	                                @RequestParam(defaultValue = "9") int size) {
	        // Retrieve projects by keywords
	        List<PostProjectsModel> projectsByKeywords = projectRepo.findByKeywordsContaining(projectName);

	        // Retrieve projects by name
	        List<PostProjectsModel> projectsByName = projectRepo.findByNameContaining(projectName);

	        // Combine the results from both searches
	        List<PostProjectsModel> combinedProjects = new ArrayList<>();
	        combinedProjects.addAll(projectsByKeywords);
	        combinedProjects.addAll(projectsByName);

	        // Create a PageRequest for pagination
	        PageRequest pageRequest = PageRequest.of(page, size);

	        // Create a Page of projects
	        int start = (int) pageRequest.getOffset();
	        int end = (start + pageRequest.getPageSize()) > combinedProjects.size() ? combinedProjects.size() : (start + pageRequest.getPageSize());
	        Page<PostProjectsModel> projectsPage = new PageImpl<>(combinedProjects.subList(start, end), pageRequest, combinedProjects.size());

	        model.addAttribute("projects", projectsPage.getContent());
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", projectsPage.getTotalPages());

	        model.addAttribute("email", (String) session.getAttribute("email"));
	        return "view-projects";
	    }
	
	@PostMapping("/postProjects")
	public ModelAndView postProjectsForm(@ModelAttribute PostProjectsModel pps,HttpSession session, Model model) {
	    System.out.println(pps.toString());

    	String email = (String) session.getAttribute("email");
    	 User user= userRepo.findById(email).orElse(null);
    	 String userName;
    	 if(user!=null)
    	 {
    	 userName = user.getUsrName();
    	 }
    	 else
    		 userName = bearcat.getUsrName();
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
    public String viewProjects(Model model, HttpSession session,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "9") int size) throws java.text.ParseException {

        // Retrieve all projects from the repository
        List<PostProjectsModel> allProjects = projectRepo.findAll();

        // Create a list to store expired projects
        List<PostProjectsModel> expiredProjects = new ArrayList<>();

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("MM/dd/yyyy"); // Initialize outputDateFormat

        // Filter expired projects
        for (PostProjectsModel pps : allProjects) {
            String startDateString = pps.getExpiryDate();
            try {
                Date expDate = inputDateFormat.parse(startDateString);

                if (expDate.after(new Date()) && pps.getApproved()=='a') {
                    pps.setExpiryDate(outputDateFormat.format(expDate));
                    expiredProjects.add(pps);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Create a PageRequest for pagination
        PageRequest pageRequest = PageRequest.of(page, size);

        // Convert the list of expired projects to a Page
        int start = (int) pageRequest.getOffset();
        int end = (start + pageRequest.getPageSize()) > expiredProjects.size() ? expiredProjects.size() : (start + pageRequest.getPageSize());
        Page<PostProjectsModel> projectsPage = new PageImpl<>(expiredProjects.subList(start, end), pageRequest, expiredProjects.size());

        model.addAttribute("projects", projectsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", projectsPage.getTotalPages());

        model.addAttribute("email", (String) session.getAttribute("email"));
        return "view-projects";
    }
	
	@GetMapping("/viewProjects/expired")
	public String viewProjectsExp(Model model, HttpSession session,
	                              @RequestParam(defaultValue = "0") int page,
	                              @RequestParam(defaultValue = "9") int size) throws java.text.ParseException {

	    // Retrieve all projects from the repository
	    List<PostProjectsModel> allProjects = projectRepo.findAll();

	    // Create a list to store expired projects
	    List<PostProjectsModel> expiredProjects = new ArrayList<>();

	    SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat outputDateFormat = new SimpleDateFormat("MM/dd/yyyy");

	    // Filter expired projects
	    for (PostProjectsModel pps : allProjects) {
	        String startDateString = pps.getExpiryDate();
	        try {
	            Date expDate = inputDateFormat.parse(startDateString);

	            if (expDate.before(new Date())&&pps.getApproved()=='a') {
	                pps.setExpiryDate(outputDateFormat.format(expDate));
	                expiredProjects.add(pps);
	            }
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	    }

	    // Create a PageRequest for pagination
	    PageRequest pageRequest = PageRequest.of(page, size);

	    // Convert the list of expired projects to a Page
	    int start = (int) pageRequest.getOffset();
	    int end = (start + pageRequest.getPageSize()) > expiredProjects.size() ? expiredProjects.size() : (start + pageRequest.getPageSize());
	    Page<PostProjectsModel> projectsPage = new PageImpl<>(expiredProjects.subList(start, end), pageRequest, expiredProjects.size());

	    model.addAttribute("projects", projectsPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", projectsPage.getTotalPages());

	    model.addAttribute("email", (String) session.getAttribute("email"));
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
	        String profilePic ="";
	        
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

		         byte[] profilePicture = user.getProfilePicture();
		         model.addAttribute("profilePicture", profilePicture != null ? Base64.getEncoder().encodeToString(profilePicture) : "");
	    	}
	    	else if(bearcat!=null)
	    	{

	    	    model.addAttribute("username", bearcat.getUsrName());
	    		
	    	}
	    	List<Comments> comments = commentRepo.findByProjectId(projectName);
	    	Collections.reverse(comments);

	    	List<Comments> updatedComments = new ArrayList<>();  // Collect updated comments
	    	List<Subcomments> updatedSubcomments = new ArrayList<>();  // Collect updated subcomments

	    	for (Comments comment : comments) {
	    	    String commenterEmail = comment.getUsrName(); 
	    	    System.out.println("Usernames for comments"+commenterEmail);
	    	    User commenter = userRepo.findByUsrName(commenterEmail);
	    	    if (commenter != null) {
	    	    	System.out.println(commenter.getProfilePicture() + "URL");
	    	        comment.setProfilePicture(Base64.getEncoder().encodeToString(commenter.getProfilePicture()));
	    	        System.out.println(comment.getProfilePicture() + " get pro pic");
	    	        // Add null check for replies
	    	        List<Subcomments> subcomments = comment.getReplies();
	    	        if (subcomments != null) {
	    	            for (Subcomments subcomment : subcomments) {
	    	                String subcommenterEmail = subcomment.getUsrName();
	    	                User subcommenter = userRepo.findByUsrName(subcommenterEmail);
	    	                if (subcommenter != null) {
	    	                    subcomment.setProfilePicture(subcommenter.getProfilePicture() != null ? Base64.getEncoder().encodeToString(subcommenter.getProfilePicture()) : "");
	    	                    updatedSubcomments.add(subcomment);
	    	                }
	    	            }
	    	        }
	    	        updatedComments.add(comment);
	    	    }
	    	}

	    	// Save all comments and subcomments outside the loop
	    	commentRepo.saveAll(updatedComments);
	    	subcommentRepo.saveAll(updatedSubcomments);
	    	System.out.println("Retrieved comments for project: " + projectName);
	    	System.out.println("Number of comments: " + comments.size());
	    	model.addAttribute("email",emails);
	        model.addAttribute("comments", comments);
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
	 
	 @PostMapping("/likeProject")
	    public ResponseEntity<String> likeProject(@RequestParam String projectName) {
	        // Logic to increment the like count in the database
	        PostProjectsModel project = projectRepo.findById(projectName).orElse(null);
	        if (project != null) {
	            int currentLikes = Integer.parseInt(project.getLikes());
	            project.setLikes(String.valueOf(currentLikes + 1));
	            projectRepo.save(project);
	            return ResponseEntity.ok("Liked successfully");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
	        }
	    }

	    @PostMapping("/dislikeProject")
	    public ResponseEntity<String> dislikeProject(@RequestParam String projectName) {
	        // Logic to increment the dislike count in the database
	        PostProjectsModel project = projectRepo.findById(projectName).orElse(null);
	        if (project != null) {
	            int currentDislikes = Integer.parseInt(project.getDislikes());
	            project.setDislikes(String.valueOf(currentDislikes + 1));
	            projectRepo.save(project);
	            return ResponseEntity.ok("Disliked successfully");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
	        }
	    }
	    
	    @GetMapping("/flagUser")
	    @ResponseBody
	    public ResponseEntity<String> flagUser(@RequestParam("userMail") String userMail) {
	        try {
	            User userfound = userRepo.findById(userMail).orElse(null);

	            if (userfound != null) {
	                userfound.setFlags(userfound.getFlags() + 1);
	                userRepo.save(userfound);
	            } else {
	                Bearcat bearcat = bearcatRepo.findById(userMail).orElse(null);
	                if (bearcat != null) {
	                    bearcat.setFlags(bearcat.getFlags() + 1);
	                    bearcatRepo.save(bearcat);
	                } else {
	                    // User not found in both repositories
	                    return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	                }
	            }

	            // Return success message as JSON
	            return new ResponseEntity<>("{\"message\":\"Flagged user successfully\"}", HttpStatus.OK);
	        } catch (Exception e) {
	            // Handle exceptions and return an error message
	            return new ResponseEntity<>("{\"error\":\"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
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
