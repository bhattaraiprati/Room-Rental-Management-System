package com.example.demo.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Model.Property;
import com.example.demo.Model.User;
import com.example.demo.Model.favourite;
import com.example.demo.Repository.PropertyRepository;
import com.example.demo.Repository.Repository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.favouriteRepository;
import com.example.demo.services.EmailSendService;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class LinkedController {
	
	@Autowired
	private Repository pRepo;
	
	@Autowired
	private favouriteRepository favRepo;
	
	@Autowired
	private PropertyRepository repo;
	
	@Autowired
	private UserRepository uRepo;
	
	@Autowired
	private EmailSendService emailService;
	
	public boolean login = false;
	
	@GetMapping("/loginbtn")
	public String loginPage() {
		
		return "login.html";
	}
	
	@GetMapping("/closebtn")
	public String closebtn(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		Boolean login = (Boolean)session.getAttribute("login");
		System.out.println("session printing  "+login);
		model.addAttribute("login", login);
		
		if (userId != null) {
	        Optional<User> userOptional = uRepo.findById(userId);
	        User userList = userOptional.orElse(null);
	        model.addAttribute("userList", userList);
	    }
		return "index.html";
	}
	@GetMapping("/signup")
	public String signup() {
		
		return "signup.html";
	}
	
//	@GetMapping("/properties")
//	public String properties(HttpServletRequest request, HttpServletResponse response, Model model) {
//		
//		Pageable pageable = PageRequest.of(0, 9);
//		List<Property> propertyList = pRepo.findAllByOrderByPostDateDesc(pageable);
//		
//		HttpSession session = request.getSession();
//		
//		System.out.println("session printing  "+login);
//		model.addAttribute("propertyList", propertyList);
//		Boolean login = (Boolean)session.getAttribute("login");
//		model.addAttribute("login", login);
//		
//		Integer userId = (Integer) session.getAttribute("userId");
//	    if (userId != null) {
//	    	Optional<User> userOptional = uRepo.findById(userId);
//	        User userList = userOptional.orElse(null);
//	        model.addAttribute("userList", userList);
//	    }
//		
//		
//		return "properties.html";
//	}
	
	@GetMapping("/")
	public String landingPage(HttpServletRequest request, HttpServletResponse response, Model model) {
	    HttpSession session = request.getSession();
	    Boolean login = (Boolean) session.getAttribute("login");
	    model.addAttribute("login", login);

	    Integer userId = (Integer) session.getAttribute("userId");
	    if (userId != null) {
	    	Optional<User> userOptional = uRepo.findById(userId);
	        User userList = userOptional.orElse(null);
	        model.addAttribute("userList", userList);
	        
	        
	    }
	    Pageable pageable = PageRequest.of(0, 9, Sort.by(Sort.Direction.DESC, "postDate"));
		List<Property> propertyList = repo.findAllByOrderByPostDateDesc(pageable);
	    
	    if (!propertyList.isEmpty()) {
	        
	        // Do something with the mostRecentProperty
	        model.addAttribute("propertyList", propertyList);
	    } 
		
		
	    return "index.html";
	}


	
	@GetMapping("/addproperty")
	public String addpropertyPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		Boolean login = (Boolean)session.getAttribute("login");
		System.out.println("session printing  "+login);
		model.addAttribute("login", login);
		Integer userId = (Integer) session.getAttribute("userId");
		Optional<User> userOptional = uRepo.findById(userId);
        User userList = userOptional.orElse(null);
        model.addAttribute("userList", userList);
		return "addproperties.html";
	}
	
	@GetMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
	    HttpSession session = request.getSession();
	    Boolean login = (Boolean) session.getAttribute("login");
	    System.out.println("session printing  " + login);
	    model.addAttribute("login", login);
	    Integer userId = (Integer) session.getAttribute("userId");
	    
	    Pageable pageable = PageRequest.of(0, 9, Sort.by(Sort.Direction.DESC, "postDate"));
		List<Property> propertyList = repo.findAllByOrderByPostDateDesc(pageable);

	    // Check if userId is not null before executing findById
	    if (userId != null) {
	        Optional<User> userOptional = uRepo.findById(userId);
	        User userList = userOptional.orElse(null);
	        model.addAttribute("userList", userList);
	    }

	    
	    
	    if (!propertyList.isEmpty()) {
	        
	        // Do something with the mostRecentProperty
	        model.addAttribute("propertyList", propertyList);
	    } 
		

	    return "index.html";
	}

	
	@GetMapping("/favourite")
	public String contact(HttpServletRequest request, HttpServletResponse response, Model model) {
	    HttpSession session = request.getSession();
	    Boolean login = (Boolean) session.getAttribute("login");
	    model.addAttribute("login", login);
	    Integer userId = (Integer) session.getAttribute("userId");

	    // Check if userId is not null before using it
	    if (userId != null) {
	        List<favourite> favlist = favRepo.findByUserId(userId);

	        List<Property> propertyList = new ArrayList<>();

	        // Iterate over each favorite entity to fetch the corresponding property
	        for (favourite fav : favlist) {
	            // Extract the propertyId from the favorite entity
	            int propertyId = fav.getPropertyId();

	            // Retrieve the Property entity corresponding to the propertyId
	            Optional<Property> propertyOptional = repo.findById(propertyId);

	            // Check if the property exists
	            if (propertyOptional.isPresent()) {
	                Property property = propertyOptional.get();
	                // Add the property to the list
	                propertyList.add(property);
	            }
	        }

	        Optional<User> userOptional = uRepo.findById(userId);
	        User userList = userOptional.orElse(null);
	        model.addAttribute("userList", userList);
	        model.addAttribute("propertyList", propertyList);
	    }

	    return "favourite.html";
	}

	
	
	
	@GetMapping("/propertyDetails/{id}")
	public String propertyDetails(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response, Model model) {
	    int propertyId = id;
	    List<Property> detailsList = pRepo.findById(propertyId);
	    model.addAttribute("propertyList", detailsList);

	    HttpSession session = request.getSession();
	    Boolean login = (Boolean) session.getAttribute("login");
	    model.addAttribute("login", login);

	    Integer userId = (Integer) session.getAttribute("userId");

	    // Check if userId is not null before using it
	    if (userId != null) {
	        Optional<User> userOptional = uRepo.findById(userId);
	        User userList = userOptional.orElse(null);
	        model.addAttribute("userList", userList);
	    }

	    return "single_property.html";
	}

	
	@GetMapping("/seeDetails/{id}")
	public String seeDetails(@PathVariable("id") Integer id,HttpServletRequest request, HttpServletResponse response, Model model) {
		
		int propertyId = id;
		List<Property> detailsList = pRepo.findById(propertyId);
		model.addAttribute("propertyList", detailsList);
		HttpSession session = request.getSession();
		Boolean login = (Boolean)session.getAttribute("login");
		model.addAttribute("login", login);
		Integer userId = (Integer) session.getAttribute("userId");
		Optional<User> userOptional = uRepo.findById(userId);
        User userList = userOptional.orElse(null);
        model.addAttribute("userList", userList);
		return "adminProperty.html";
	}
	
	@GetMapping("/changePassword")
	public String changePassword() {
		
		
		return "changePassword.html";
	}
	
	@GetMapping("/editProfile")
	public String editProfile(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		HttpSession session = request.getSession();
		Boolean login = (Boolean)session.getAttribute("login");
		model.addAttribute("login", login);
		Integer userId = (Integer) session.getAttribute("userId");
		Optional<User> userOptional = uRepo.findById(userId);
        User userList = userOptional.orElse(null);
        
        System.out.println("printing the user list" + userList);
        model.addAttribute("userList", userList);
		
		return "profileEdit.html";
	}
	
	@GetMapping("/pendingPage")
	public String pendingPage(Model model) {
		
		List<Property> propertyList = pRepo.findByStatus("pending");
		
		model.addAttribute("propertyList", propertyList);
		
		return "pendingRequest.html";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		
		List<Property> propertyList = pRepo.findAll();
		
		model.addAttribute("propertyList", propertyList);
		
		return "AdminIndex.html";
	}
	
	
	@GetMapping("/approvePage")
	public String approvePage(Model model) {
		
		List<Property> propertyList = pRepo.findByStatus("Approved");
		
		model.addAttribute("propertyList", propertyList);
		
		return "approveList.html";
	}
	
	
	@GetMapping("/declinePage")
	public String declinePage(Model model) {
		
		List<Property> propertyList = pRepo.findByStatus("Decline");
		
		model.addAttribute("propertyList", propertyList);
		
		return "declineList.html";
	}
	
	@GetMapping("/seeDetailsAdmin/{id}")
	public String getMethodName(@PathVariable("id") int id, Model model) {
		
		List<Property> propertyList = pRepo.findById(id);
		model.addAttribute("propertyList", propertyList);
		
		return "adminPropertyDetails.html";
	}
	
	
	@GetMapping("/approve/{id}")
	public String approveProperty(@PathVariable("id") int id, Model model) {
	    Optional<Property> propertyOptional = repo.findById(id);
	    
	    if (propertyOptional.isPresent()) {
	        Property property = propertyOptional.get();
	        
	        String status = property.getStatus();
	        if ("Approved".equals(status)) {
	            model.addAttribute("success", "failed");
	        } else {
	        	
	        	String email = property.getContactEmail();
	        	String name = property.getContactPerson();
	        	
	        	
	        	String emailBody = "Hello "+ name+ ",\n\n" + 
	        	"We are pleased to inform you that your listed property has been approved." +"\n\n"+
	        	"Here are the details of your property:\n"+
	        	"Property name: " + property.getBedroom() +" is in rent at "+ property.getCity()+",\n"+
	        	"Location: "+ property.getPlace()+", "+ property.getCity()+"\n"+
	        	"Price: " + property.getPrice()+"\n\n\n"+
	        	"Best Regards,\n Room Rental";
	        	
	        	emailService.sendEMail(email, "Approve Notification", emailBody);
	        	
	            property.setStatus("Approved");
	            pRepo.save(property); // Save the updated property status
	            model.addAttribute("success", "success");
	        }
	    } else {
	        // Handle the case where the property with the given ID is not found
	        model.addAttribute("success", "failed");
	    }
	    
	    List<Property> propertyList = pRepo.findById(id);
	    model.addAttribute("propertyList", propertyList);
	    
	    // Return the appropriate view
	    return "adminPropertyDetails.html";
	}


	@GetMapping("/decline/{id}")
	public String declineProperty(@PathVariable("id") int id, Model model) {
	    Optional<Property> propertyOptional = repo.findById(id);
	    
	    if (propertyOptional.isPresent()) {
	        Property property = propertyOptional.get();
	        
	        String status = property.getStatus();
	        if ("Decline".equals(status)) {
	            model.addAttribute("success", "error");
	        } else {
	        	
	        	String email = property.getContactEmail();
	        	String name = property.getContactPerson();
	        	
	        	
	        	String emailBody = "Hello "+ name+ ",\n\n" + 
	        	"We are pleased to inform you that your listed property has been Decline." +"\n\n"+
	        	"Here are the details of your property:\n"+
	        	"Property name: " + property.getBedroom() +" is in rent at "+ property.getCity()+",\n"+
	        	"Location: "+ property.getPlace()+", "+ property.getCity()+"\n"+
	        	"Price: " + property.getPrice()+"\n\n\n"+
	        	"Best Regards,\nRoom Rental";
	        	
	        	emailService.sendEMail(email, "Decline Notification", emailBody);
	        	
	            property.setStatus("Decline");
	            pRepo.save(property); // Save the updated property status
	            model.addAttribute("success", "decline");
	        }
	    } else {
	        // Handle the case where the property with the given ID is not found
	        model.addAttribute("success", "error");
	    }
	    
	    List<Property> propertyList = pRepo.findById(id);
	    model.addAttribute("propertyList", propertyList);
	    
	    // Return the appropriate view
	    return "adminPropertyDetails.html";
	}
	
	@GetMapping("/forgotPassword")
	public String forgotPassword() {
		
		return "resetPassword.html";
	}
	
	
	@GetMapping("/ResetClose")
	public String ResetClose() {
		return "login.html";
	}
	
	@PostMapping("/ResetPassword")
	public String ResetPassword(@RequestParam("useremail") String email, Model model, HttpSession session) {
		
		User userExist = uRepo.findByUseremail(email);
		
		if (userExist!=null) {
			
			String otp = emailService.generateOTP();
			
			String emailBody = "Hi,\n\n" + 
		        	"Please use the following One Time Password(OTP) to access the form: " +otp+
		        	" Do not share this OTP with anyone. \n"+
		        
		        	"Thank You!";
		        	
		        	emailService.sendEMail(email, "OTP Notification", emailBody);
		        	
		        	session.setAttribute("otp", otp);
		        	
		        	session.setAttribute("findUserId", userExist.getId());
			
		        	model.addAttribute("success", "success");
		        	return "OtpVerification.html";
		}
		else {
			
			return "resetPassword.html";
			
		}
		

		
	}

	@PostMapping("/OtpVerify")
	public String OtpVerify(@RequestParam("otp") String otpEntered, Model model, HttpSession session) {
		
		 String otp = (String) session.getAttribute("otp");

		    if (otp != null && otp.equals(otpEntered)) {
		        // OTP is verified, proceed with password reset
		        // Add your password reset logic here
		        
		        // Clear OTP from session
		        session.removeAttribute("otp");

		        // Redirect to a success page or return success message
		        return "ResetChangePassword.html";
		    } else {
		        // OTP verification failed
		        model.addAttribute("error", "Invalid OTP. Please try again.");
		        return "OtpVerification.html";
		    }
		    
	}
	
	@PostMapping("/ResetChangePassword")
	public String ResetChangePassword(@RequestParam("password") String password, Model model, HttpSession session) {
		//TODO: process POST request
		
		 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		int findUserId = (int) session.getAttribute("findUserId");
		
		User userList = uRepo.findById(findUserId);
		
		if (userList != null) {
			
			String encryptedNewPassword = passwordEncoder.encode(password);
			userList.setPassword(encryptedNewPassword);
			
			uRepo.save(userList);
			model.addAttribute("success", "success");
			
			
		}
		
		
		
		
		return "login.html";
	}
	
	
	
	
	
	
	
	
}
