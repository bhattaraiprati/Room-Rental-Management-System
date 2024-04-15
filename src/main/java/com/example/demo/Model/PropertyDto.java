package com.example.demo.Model;

import java.sql.Date;

public class PropertyDto {
	
	private int id;
	private String city;
	private String place;
	private int price;
	private String propertyType;
	private String bedroom;
	private String contactPerson;
	private String contactNumber;
	private String contactEmail;
	
	private String description;
	
	private int userId;
	private String status;
	private Date postDate;
	private Date expiryDate;
	private String livingRoom;
	private String bathroom;
	private String roadType;
	private String floor;
	private String tole;
	
	
	
//	private Property property;
//    private Image image;
//	
	
	

	
	public int getId() {
		return id;
	}
	public String getTole() {
		return tole;
	}
	public void setTole(String tole) {
		this.tole = tole;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getBedroom() {
		return bedroom;
	}
	public void setBedroom(String bedroom) {
		this.bedroom = bedroom;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getLivingRoom() {
		return livingRoom;
	}
	public void setLivingRoom(String livingRoom) {
		this.livingRoom = livingRoom;
	}
	public String getBathroom() {
		return bathroom;
	}
	public void setBathroom(String bathroom) {
		this.bathroom = bathroom;
	}
	public String getRoadType() {
		return roadType;
	}
	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	
}
