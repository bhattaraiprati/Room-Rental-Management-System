package com.example.demo.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Property;


import com.example.demo.Repository.Repository;


@Service
public class PropertyServiceImpl implements PropertyService {
	
	
	
	@Autowired
	private Repository repo;

	
	private static final String UPLOAD_DIR = "C://Users//Pratik//eclipse-workspace//RoomRentalSystem//src//main//resources//static//uploadImg/";

    public void saveFile(MultipartFile file, String fileName) throws IOException {
        String filePath = UPLOAD_DIR + fileName;
        File dest = new File(filePath);
        file.transferTo(dest);
    }


}
