package com.example.demo.Controller;


import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Model.Property;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.PropertyRepository;
import com.example.demo.Repository.Repository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;





@Controller
public class userController {
	@Autowired
	private UserRepository uRepo;
	
	@Autowired
	private Repository pRepo;
	
	@Autowired 
	private PropertyRepository repo;
	
	private boolean login = false;
	
	@PostMapping("/signup")
	public String register(@ModelAttribute User u, Model model) {
		
		
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		
		System.out.println("printing the image for profile  "+u.getImage());
		
		if (uRepo.findByUseremail(u.getUseremail())== null) {
			String encryptedpwd = bcrypt.encode(u.getPassword());
			u.setPassword(encryptedpwd);
			u.setImage("roomProfile.jpg");
			uRepo.save(u);
			return "login.html";
		}
		else {
			model.addAttribute("error", "Email is already exist");
			return "signup.html";
		}
		
		
	}
	
	@PostMapping("/login")
	public String userLogin(@ModelAttribute User u, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		BCryptPasswordEncoder pcrypt = new BCryptPasswordEncoder();
		
		
		User opUser = uRepo.findByUseremail(u.getUseremail());
		
		HttpSession session = request.getSession();
		
		String adminEmail = "Admin@gmail.com";
		String adminPassword = "admin";
		
		if (adminEmail.equals(u.getUseremail()) && adminPassword.equals(u.getPassword())) {
			
			List<Property> propertyList = pRepo.findAll();
			
			model.addAttribute("propertyList", propertyList);
			
			return "AdminIndex.html";
			
		}
		
		
		if (opUser != null && pcrypt.matches(u.getPassword(), opUser.getPassword())) {
			login = true;
			int userId = opUser.getId();
			String success = "success";
			
			session.setAttribute("userId", userId);
			session.setAttribute("login", login);
			
			User userList = uRepo.findById(userId);
			
			Pageable pageable = PageRequest.of(0, 9, Sort.by(Sort.Direction.DESC, "postDate"));
			List<Property> propertyList = repo.findAllByOrderByPostDateDesc(pageable);
		    
		    if (!propertyList.isEmpty()) {
		        
		        // Do something with the mostRecentProperty
		        model.addAttribute("propertyList", propertyList);
		    } 
			
			model.addAttribute("success", success);
			model.addAttribute("login", login);
			model.addAttribute("userList", userList);
			
			 return "index.html";
			 
	    } else {
	    	String invalid = "Invalid Email and Password";
	    	model.addAttribute("invalid", invalid);
	    	return "login.html";
	       
	    }
	}
	
	@GetMapping("/logout")
	public String userLogout(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		session.removeAttribute("login");
		session.invalidate();
		return "index.html";
	}
	
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("oldPassword") String oldPass, 
	                             @RequestParam("newPassword") String newPass, 
	                             HttpServletRequest request, 
	                             Model model) {
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    HttpSession session = request.getSession();
	    int userId = (int) session.getAttribute("userId");
	    
	    User user = uRepo.findById(userId);
	    
	    if (user != null && passwordEncoder.matches(oldPass, user.getPassword())) {
	        // Valid old password
	        String encryptedNewPassword = passwordEncoder.encode(newPass);
	        user.setPassword(encryptedNewPassword);
	        uRepo.save(user);
	        User userList = uRepo.findById(userId);
			model.addAttribute("userList", userList);
	        
	        // Redirect to a success page
	        return "redirect:/index";
	    } else {
	        // Invalid old password
	        String errorMessage = "Invalid old password";
	        model.addAttribute("errorMessage", errorMessage);
	        return "changePassword.html";
	    }
	}

	
	
		
		
	
		
	}


