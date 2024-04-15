package com.example.demo;

import javax.swing.Spring;

import org.hibernate.type.TrueFalseConverter;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;



@SpringBootApplication
public class RoomRentalSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomRentalSystemApplication.class, args);
		
	}
	
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	  if (!registry.hasMappingForPattern("/static/**")) {
	     registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	  }
	}
}
