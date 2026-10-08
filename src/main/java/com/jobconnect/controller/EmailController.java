package com.jobconnect.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jobconnect.service.EmailService;

@RestController
public class EmailController {
	private final EmailService emailService;
	public EmailController(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@PostMapping("/test-email")
	public String testEmail(@RequestParam String to) {
		emailService.sendTestEmail(to);
		return "Test email sent successfully";
	}

}
