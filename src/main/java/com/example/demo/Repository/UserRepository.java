package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.User;
import java.util.List;
import java.util.Optional;






public interface UserRepository extends JpaRepository<User, Integer> {
	
	User  findByUseremailAndPassword(String useremail, String password);
	
	boolean existsByUseremailAndPassword(String useremail, String password);
	
	User findByUseremail(String useremail);
	
	User findById(int id);
//	Optional<User> findById(Integer id);

	
	

}
