package com.example.demo.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	public void sendEMail(String toEmail, String subject, String body) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("bhattaraipratik44@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		
		mailSender.send(message);
		
		System.out.println("mail send sucessfully....");
		
	}
	
	public String generateOTP() {
        // Define the length of OTP
        int otpLength = 4;

        // Define the characters to be used for OTP generation
        String numbers = "0123456789";

        // Using StringBuilder to efficiently append characters
        StringBuilder sb = new StringBuilder(otpLength);

        // Create a new random object
        Random random = new Random();

        // Generate OTP of specified length
        for (int i = 0; i < otpLength; i++) {
            // Generate a random index between 0 and length of numbers string
            int index = random.nextInt(numbers.length());

            // Append the character at the randomly generated index to the OTP string
            sb.append(numbers.charAt(index));
        }

        // Return the generated OTP
        return sb.toString();
    }

}
