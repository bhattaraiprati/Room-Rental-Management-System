package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class favourite {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int favId;
	
	private int userId;
	private int propertyId;
	
	
	public int getFavId() {
		return favId;
	}
	public void setFavId(int favId) {
		this.favId = favId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}
	

	
}




