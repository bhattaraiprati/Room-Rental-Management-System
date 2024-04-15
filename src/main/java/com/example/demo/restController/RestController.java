package com.example.demo.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.Model.Property;
import com.example.demo.Model.User;
import com.example.demo.Repository.Repository;
import com.example.demo.Repository.UserRepository;


@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	
	@Autowired
	private Repository repo;
	
	@Autowired
	private UserRepository uRepo;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	
	@PostMapping("/api/postData")
	
	public String addProperty(@RequestBody Property p) {
		
		repo.save(p);
		
		return "saved";
	}
	@GetMapping("/api/getData")
	public List<Property> getProperty() {
		
		List<Property> list = repo.findAll();
		
		return list;
		
	}
	@PostMapping("/api/register")
	public String register(@RequestBody User u) {
		//TODO: process POST request
		
//		String pass  = u.getPassword();
		
//		String encryptedpwd = this.passwordEncoder.encode(pass);
//		u.setPassword(encryptedpwd);
//		System.out.println("encrypted password is = " + encryptedpwd);
		uRepo.save(u);
		
		return "Encrypted sucessfully";
	}
	
	
}
