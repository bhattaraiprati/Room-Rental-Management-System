package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Property;
import com.example.demo.Model.User;

public interface Repository extends JpaRepository<Property, Integer> {
	
	List<Property> findById(int id);
//	Optional<Property> findById(int id);
	
	List<Property> findByUserId(int userId);
	
	List<Property> findByCity(String city);
	List<Property> findByPlace(String place);
	List<Property> findByCityOrPlaceAndPriceAndBedroom(String city, String place, int price, String bedroom);
	List<Property> findByCityOrPlace(String city, String place);
	List<Property> findByCityOrPlaceOrPriceOrBedroom(String city, String place, int price, String bedroom);
	List<Property> findByStatus(String status);
	
	 List<Property> findAllByOrderByPostDateDesc(Pageable pageable);
	 List<Property> findAllByOrderByPriceDesc();
	 List<Property> findAllByOrderByPriceAsc();
}
