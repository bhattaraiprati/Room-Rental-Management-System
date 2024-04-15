package com.example.demo.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Property;


@Service
public interface PropertyService {
	
	
	
	
	public void saveFile(MultipartFile file, String fileName) throws IOException;

}
