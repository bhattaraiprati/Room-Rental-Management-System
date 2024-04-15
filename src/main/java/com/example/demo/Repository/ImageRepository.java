package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.PropertyImage;

public interface ImageRepository extends JpaRepository<PropertyImage, Integer>{
	
	

}
