package com.jobconnect.service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
	private final JavaMailSender mailSender;
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendTestEmail(String to) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(to);
		message.setSubject("JobConnect Test Email");
		message.setText("Hello!\n\n" + "This is a test email from your JobConnect application.\n\n" + "Email service is working successfully!");
		
		mailSender.send(message);
	}
	
	public void sendApplicationEmail(String recruiterEmail, String applicantName, String jobTitle) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(recruiterEmail);
		System.out.println("RECRUITER EMAIL = " + recruiterEmail);
		message.setSubject("New Job Application Received");
		message.setText("Hello,\n\n" + applicantName + "has applied for your job: " + jobTitle + ".\n\n" + "Please login to JobConnect to view the application.\n\n" + "Regards,\n" + "JobConnect");
		
		mailSender.send(message);
	}

}
