package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Property;

import org.springframework.data.domain.Pageable;
import java.util.List;


public interface PropertyRepository extends JpaRepository<Property, Integer> {
	
	 Optional<Property> findById(int id);
	 
	 Property findByIdAndStatus(int id, String status);
	 
	 List<Property> findAllByOrderByPostDateDesc(Pageable pageable);

}
