package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.favourite;

@Repository
public interface favouriteRepository extends JpaRepository<favourite, Integer> {
	
	void deleteByPropertyId(int propertyId);
	List<favourite> findByUserId(int userId);
	
	List<favourite> findByUserIdAndPropertyId(int userId, int propertyId);
	
}
