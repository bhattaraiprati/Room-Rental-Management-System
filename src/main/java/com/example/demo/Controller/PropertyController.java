package com.example.demo.Controller;


import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.http11.Http11InputBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Model.Property;
import com.example.demo.Model.PropertyDto;
import com.example.demo.Model.PropertyImage;
import com.example.demo.Model.User;
import com.example.demo.Model.favourite;
import com.example.demo.Model.userDto;
import com.example.demo.Repository.ImageRepository;
import com.example.demo.Repository.PropertyRepository;
import com.example.demo.Repository.Repository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.favouriteRepository;
import com.example.demo.services.PropertyService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.websocket.Session;

@Controller
public class PropertyController {
	
	@Autowired
	private Repository pRepo;

	
	@Autowired
	private PropertyService services;
	
	@Autowired
	private favouriteRepository favRepo;
	
	@Autowired
	private PropertyRepository repo;
	
	@Autowired
	private UserRepository uRepo;
	
	@Autowired
	private ImageRepository imageRepo;

	
	

	@PostMapping("/addProperty")
	public String addProperty( @RequestParam("image") MultipartFile[] files,@ModelAttribute PropertyDto p, Model model,HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
		

			LocalDate localDate = LocalDate.now();
			Date date = Date.valueOf(localDate);
			HttpSession session = request.getSession();
			
			 int Userid = (int) session.getAttribute("userId");
	        
	        Property property = new Property();
	        
	        property.setCity(p.getCity());
	        property.setPlace(p.getPlace());
	        property.setPrice(p.getPrice());
	        property.setPropertyType(p.getPropertyType());
	        property.setBedroom(p.getBedroom());
	        property.setContactPerson(p.getContactPerson());
	        property.setContactNumber(p.getContactNumber());
	        property.setContactEmail(p.getContactEmail());
	        property.setDescription(p.getDescription());
	        property.setTole(p.getTole());
	        property.setBathroom(p.getBathroom());
	        property.setExpiryDate(p.getExpiryDate());
	        property.setLivingRoom(p.getLivingRoom());
	        property.setFloor(p.getFloor());
	        property.setRoadType(p.getRoadType());
	        property.setUserId(p.getUserId());
	        property.setPostDate(date);
	        property.setUserId(Userid);
	        property.setExpiryDate(p.getExpiryDate());
	        property.setStatus(p.getStatus());
	        
	        
	        
	        Property savedProperty = pRepo.save(property);
	        // Set the image in the property
//	        property.setImage(img);
//	        String FileName = files.getOriginalFilename();
//	        services.saveFile(files, FileName);
//	        
//	        PropertyImage image = new PropertyImage();
	        
	        for (MultipartFile file : files) {
	            String fileName = file.getOriginalFilename();
	            services.saveFile(file, fileName);

	            // Create a new PropertyImage and associate it with the property
	            PropertyImage image = new PropertyImage();
	            image.setImage(fileName);
	            image.setProperty(savedProperty);
	            
	            // Save the PropertyImage
	            imageRepo.save(image);
	        }
	       
	        
//	        property.setImage(FileName);
	       
//	       pRepo.save(property);
	       
	       
	       List<Property> propertyList = pRepo.findAll();
			model.addAttribute("propertyList", propertyList);
			Boolean login = (Boolean)session.getAttribute("login");
			model.addAttribute("login", login);
			
	       
			return "redirect:/addlisting";
//	      return "listedproperty.html";
	    
	    
	    
	}
	
	@GetMapping("/propertyAdded")
	public String propertyAdded(Model model, HttpServletRequest request, HttpServletResponse response) {
	    // Render a response to indicate successful submission
		HttpSession session = request.getSession();
		List<Property> propertyList = pRepo.findAll();
		model.addAttribute("propertyList", propertyList);
		int userId = (Integer) session.getAttribute("userId");
		model.addAttribute("userId", userId);
		Boolean login = (Boolean)session.getAttribute("login");
		model.addAttribute("login", login);
		User userList = uRepo.findById(userId);
		model.addAttribute("userList", userList);
		return "listedproperty.html"; // Render a success page
	}
	
	@GetMapping("/addlisting")
	public String listedProperty(Model model, HttpServletRequest request, HttpServletResponse response) {

	    HttpSession session = request.getSession();

	    Integer userId = (Integer) session.getAttribute("userId");

	    if (userId != null) {
	        List<Property> propertyList = pRepo.findByUserId(userId);
	        model.addAttribute("propertyList", propertyList);
	        model.addAttribute("userId", userId);
	        Boolean login = (Boolean) session.getAttribute("login");
	        model.addAttribute("login", login);

	        // Check if userId is not null before executing findById
	        if (userId != null) {
	            Optional<User> userOptional = uRepo.findById(userId);
	            User userList = userOptional.orElse(null);
	            model.addAttribute("userList", userList);
	        }

	        return "listedproperty.html";
	    } else {
	       
	        return "listedproperty.html";
	    }
	}

	
	@GetMapping("/search")
	public String searchProperty(@RequestParam("search") String search, Model model) {
		
		List<Property> searchproperty = pRepo.findByCity(search);
		List<Property> placeSearch = pRepo.findByPlace(search);
		if (!searchproperty.isEmpty()) {
			
			model.addAttribute("propertyList", searchproperty);
			return "properties.html";
		}
		else if (!placeSearch.isEmpty()) {
			
			model.addAttribute("propertyList", placeSearch);
			return "properties.html";
		}
		else {
			
			String errormsg = "Sorry No Reasult Found";
			model.addAttribute("errormsg", errormsg);
			return "properties.html";
		}
	}
	
	@GetMapping("/filter")
	public String propertyFilter(@RequestParam("location") String location, @RequestParam("price") Integer price, @RequestParam("bedroom") String bedroom, Model model) {
		
		List<Property> allList = pRepo.findByCityOrPlaceAndPriceAndBedroom(location, location, price, bedroom);
		List<Property> optionSearch = pRepo.findByCityOrPlaceOrPriceOrBedroom(location, location, price, bedroom);
		model.addAttribute("propertyList", allList);
		
		if (!allList.isEmpty()) {
			
			model.addAttribute("propertyList", allList);
			return "properties.html";
		}
		else if (!optionSearch.isEmpty()) {
			
			model.addAttribute("propertyList", optionSearch);
			return "properties.html";
		}
		else {
			
			String errormsg = "Sorry No Reasult Found";
			model.addAttribute("errormsg", errormsg);
			return "properties.html";
		}

	}
	

	
		
	@GetMapping("/save/{id}")
	public String saveProperty(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Integer id, Model model) {
		
		
		HttpSession session = request.getSession();
		 
		Integer userId = (Integer) session.getAttribute("userId");
		int propertyId = id;
		
		
		
		if (userId != null) {
		    String success = "success";
		    favourite fav = new favourite();
		    
		   
		    List<favourite> favList = favRepo.findByUserIdAndPropertyId(userId, propertyId);
		    if (!favList.isEmpty()) {
		        String exist = "exist";
		        model.addAttribute("exist", exist);
		        List<Property> detailsList = pRepo.findById(propertyId);
		        model.addAttribute("propertyList", detailsList);
		        Optional<User> userOptional = uRepo.findById(userId);
	            User userList = userOptional.orElse(null);
	            model.addAttribute("userList", userList);
		        return "single_property.html";
		    } else {
		        fav.setUserId(userId);
		        fav.setPropertyId(propertyId);
		        
		        favRepo.save(fav);
		        
		        List<Property> detailsList = pRepo.findById(propertyId);
		        model.addAttribute("propertyList", detailsList);
		        
		        Boolean login = (Boolean) session.getAttribute("login");
		        model.addAttribute("login", login);
		        
		        model.addAttribute("success", success);
		        Optional<User> userOptional = uRepo.findById(userId);
	            User userList = userOptional.orElse(null);
	            model.addAttribute("userList", userList);
		        return "single_property.html";
		    }
		} else {
		    String success = "failed";
		   
		    
		    List<Property> detailsList = pRepo.findById(propertyId);
		    model.addAttribute("propertyList", detailsList);
		    
//		    Boolean login = (Boolean) session.getAttribute("login");
//		    model.addAttribute("login", login);
		    model.addAttribute("success", success);
//		    Optional<User> userOptional = uRepo.findById(userId);
//            User userList = userOptional.orElse(null);
//            model.addAttribute("userList", userList);
		    return "single_property.html";
		}

		
		
		
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProperty(Model model, @PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		String deletemsg = "deleted";
		
		int properId = id;
		model.addAttribute("delete", deletemsg);
		model.addAttribute("propertyId", properId);
		HttpSession session = request.getSession();
		Boolean login = (Boolean)session.getAttribute("login");
		model.addAttribute("login", login);
		int userId = (int) session.getAttribute("userId");
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
	    User userList = uRepo.findById(userId);
		model.addAttribute("userList", userList);
	    model.addAttribute("propertyList", propertyList);
		
		return "favourite.html";
	}
	
	
	@GetMapping("/deleteProperty/{id}")
	@Transactional
	public String successfulDelete(@PathVariable("id") Integer id) {
		
		
		
		favRepo.deleteByPropertyId(id);
		
		
		return "redirect:/favourite";
	}
	
	
	
	
	@GetMapping("/deleteAddProperty/{id}")
	public String deleteAddProperty(Model model, @PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		String deletemsg = "deleted";
		
		int properId = id;
		
		
		HttpSession session = request.getSession();
		
		Integer userId = (Integer) session.getAttribute("userId");
	
	    if (userId != null) {
			List<Property> propertyList = pRepo.findByUserId(userId);
			model.addAttribute("propertyList", propertyList);
			 model.addAttribute("userId", userId);
			 Boolean login = (Boolean)session.getAttribute("login");
			 model.addAttribute("propertyId", properId);
				model.addAttribute("login", login);
				model.addAttribute("delete", deletemsg);
				Optional<User> userOptional = uRepo.findById(userId);
		        User userList = userOptional.orElse(null);
		        model.addAttribute("userList", userList);
			return "listedproperty.html";
		}
	    else {
	    	Optional<User> userOptional = uRepo.findById(userId);
	        User userList = userOptional.orElse(null);
	        model.addAttribute("userList", userList);
			return "listedproperty.html";
		}
		
	}
	
	@GetMapping("/confirmDelete/{id}")
	@Transactional
	public String confirmDelete(@PathVariable("id") Integer id) {
		
		pRepo.deleteById(id);
		
		return "redirect:/addlisting";
	}
	
	@GetMapping("/edit/{id}")
	public String editProperty(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		int propertyId = id;
		List<Property> detailsList = pRepo.findById(propertyId);
		model.addAttribute("propertyList", detailsList);
		HttpSession session = request.getSession();
		
		Integer userId = (Integer) session.getAttribute("userId");
		Boolean login = (Boolean)session.getAttribute("login");
		model.addAttribute("login", login);
		Optional<User> userOptional = uRepo.findById(userId);
        User userList = userOptional.orElse(null);
        model.addAttribute("userList", userList);
		return "editProperty.html";
		
	}
	@PostMapping("/editProperty")
	public String editProperty(@RequestParam("image") MultipartFile[] files, @ModelAttribute PropertyDto p, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
	    LocalDate localDate = LocalDate.now();
	    Date date = Date.valueOf(localDate);
	    HttpSession session = request.getSession();
	    
	    int userId = (int) session.getAttribute("userId");

	    // Retrieve the property by its ID
	    Optional<Property> propertyOptional = repo.findById(p.getId());
	    
	    if (propertyOptional.isPresent()) {
	        Property property = propertyOptional.get();
	        
	        // Update the property fields
	        property.setCity(p.getCity());
	        property.setPlace(p.getPlace());
	        property.setPrice(p.getPrice());
	        property.setPropertyType(p.getPropertyType());
	        property.setBedroom(p.getBedroom());
	        property.setContactPerson(p.getContactPerson());
	        property.setContactNumber(p.getContactNumber());
	        property.setContactEmail(p.getContactEmail());
	        property.setDescription(p.getDescription());
	        property.setTole(p.getTole());
	        property.setBathroom(p.getBathroom());
	        property.setExpiryDate(p.getExpiryDate());
	        property.setLivingRoom(p.getLivingRoom());
	        property.setFloor(p.getFloor());
	        property.setRoadType(p.getRoadType());
//	        property.setUserId(p.getUserId()); // Check if you want to update this
	        property.setExpiryDate(p.getExpiryDate());
	        property.setStatus(p.getStatus());
	        property.setId(p.getId());
	        
	        // Save the updated property entity
	        Property savedProperty = pRepo.save(property);
	        // Set the image in the property
//	        property.setImage(img);
//	        String FileName = files.getOriginalFilename();
//	        services.saveFile(files, FileName);
//	        
//	        PropertyImage image = new PropertyImage();
	        
	        for (MultipartFile file : files) {
	            String fileName = file.getOriginalFilename();
	            services.saveFile(file, fileName);

	            // Create a new PropertyImage and associate it with the property
	            PropertyImage image = new PropertyImage();
	            image.setImage(fileName);
	            image.setProperty(savedProperty);
	            
	            // Save the PropertyImage
	            imageRepo.save(image);
	        }

	        // Add attributes to the model for rendering the view
	        List<Property> propertyList = pRepo.findAll();
	        model.addAttribute("propertyList", propertyList);
	        Boolean login = (Boolean) session.getAttribute("login");
	        model.addAttribute("login", login);

	        return "redirect:/addlisting";
	    } else {
	        // Handle the case where property with given ID is not found
	        // For example, you could add an error message to the model
	        model.addAttribute("errorMessage", "Property not found");
	        return "editProperty.html"; // Define an errorPage view
	    }
	}

	
	@PostMapping("/profileEdited")
	public String editProfile(@RequestParam("image") MultipartFile file, @ModelAttribute userDto u, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
	    HttpSession session = request.getSession();
	    int userId = (int) session.getAttribute("userId");
	    
	    User user = uRepo.findById(userId);
	    if (user != null) {
	        // Update only the fields that you want to change
	        user.setFirstName(u.getFirstName());
	        user.setLastName(u.getLastName());
	        user.setUsername(u.getUsername());
	        
	        String fileName = file.getOriginalFilename();
	        services.saveFile(file, fileName);
	        user.setImage(fileName);
	        
	        // Save the updated user entity
	        uRepo.save(user);
	        
	        // Add attributes to the model for rendering the view
	        Boolean login = (Boolean) session.getAttribute("login");
	        model.addAttribute("login", login);
	        model.addAttribute("userList", user);
	    } else {
	        // Handle the case where user with given ID is not found
	        // For example, you could add an error message to the model
	        model.addAttribute("errorMessage", "User not found");
	    }
	    
	    return "profileEdit.html";
	}
	
	@GetMapping("/pagination1")
	public String Pagination_number5(Model model) {
		
		Pageable pageable = PageRequest.of(0, 3);
		List<Property> propertyList = pRepo.findAllByOrderByPostDateDesc(pageable);
		
		model.addAttribute("propertyList", propertyList);
		
		return "properties.html";
	}
	
	@GetMapping("/pagination2")
	public String Pagination_number1(Model model) {
		
		Pageable pageable = PageRequest.of(1, 3);
		List<Property> propertyList = pRepo.findAllByOrderByPostDateDesc(pageable);
		
		model.addAttribute("propertyList", propertyList);
		
		return "properties.html";
	}
	@GetMapping("/pagination3")
	public String Pagination_number2(Model model) {
		
		Pageable pageable = PageRequest.of(2, 3);
		List<Property> propertyList = pRepo.findAllByOrderByPostDateDesc(pageable);
		
		model.addAttribute("propertyList", propertyList);
		
		return "properties.html";
	}
	@GetMapping("/pagination4")
	public String Pagination_number3(Model model) {
		
		Pageable pageable = PageRequest.of(3, 3);
		List<Property> propertyList = pRepo.findAllByOrderByPostDateDesc(pageable);
		
		model.addAttribute("propertyList", propertyList);
		
		return "properties.html";
	}
	@GetMapping("/pagination5")
	public String Pagination_number4(Model model) {
		
		Pageable pageable = PageRequest.of(4, 3);
		List<Property> propertyList = pRepo.findAllByOrderByPostDateDesc(pageable);
		
		model.addAttribute("propertyList", propertyList);
		
		return "properties.html";
	}
	
	@GetMapping("/properties")
	public String listProperties(@RequestParam(name = "sort", defaultValue = "recent") String sort, Model model, HttpServletRequest request) {
	    List<Property> propertyList;
	    HttpSession session = request.getSession();
	    Integer userId = (Integer) session.getAttribute("userId");
	    
	    if (userId != null) {
	    	
	    	Optional<User> userOptional = uRepo.findById(userId);
	        User userList = userOptional.orElse(null);
	        model.addAttribute("userList", userList);
	    }
	    
	    if (sort.equals("lowToHigh")) {
	        propertyList = pRepo.findAllByOrderByPriceAsc();
	    } else if (sort.equals("highToLow")) {
	        propertyList = pRepo.findAllByOrderByPriceDesc();
	    } else {
	        // Default sorting, recent
	    	Pageable pageable = PageRequest.of(0, 9);
			 propertyList = pRepo.findAllByOrderByPostDateDesc(pageable);
	    }
	    
	    Boolean login = (Boolean)session.getAttribute("login");
		model.addAttribute("login", login);
	    
	    model.addAttribute("propertyList", propertyList);
	    
	    
	    
	    return "properties.html";
	}
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	

}
