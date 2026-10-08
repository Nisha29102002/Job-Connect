package com.jobconnect.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jobconnect.entity.Application;
import com.jobconnect.entity.Job;
import com.jobconnect.entity.User;
import com.jobconnect.repository.JobRepository;
import com.jobconnect.repository.UserRepository;
import com.jobconnect.service.ApplicationService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
public class ApplicationController {
	private final ApplicationService applicationService;
	private final UserRepository userRepository;
	private final JobRepository jobRepository;
	
	public ApplicationController(ApplicationService applicationService, UserRepository userRepository, JobRepository jobRepository) {
		this.applicationService = applicationService;
		this.userRepository = userRepository;
		this.jobRepository = jobRepository;
	}
	
	@PostMapping("/applications")
		public Application applyForJob(@RequestParam Long jobId, Authentication authentication) {
		  System.out.println("Logged in email: " + authentication.getName());
			User applicant = userRepository.findByEmail(authentication.getName()).orElseThrow();
			
			Job job = jobRepository.findById(jobId).orElseThrow();
			
			Application application = new Application();
			
			application.setApplicant(applicant);
			application.setJob(job);
			
			application.setStatus("APPLIED");
			
			return 
					applicationService.createApplication(application);
		
	}
	
	@PutMapping("/applications/{applicationId}/status")
	public Application updateStatus(@PathVariable Long applicationId, @RequestParam String status, Authentication authentication) {
		return
				applicationService.updateStatus(applicationId, status, authentication.getName());
	}
	
	@GetMapping("/applications/my")
		public List<Application> getMyApplications(Authentication authentication){
			User applicant = userRepository.findByEmail(authentication.getName())
					.orElseThrow();
			return
					applicationService.getApplicationsByApplicant(applicant.getId());
		}
	
	@GetMapping("/applications/job/{jobId}")
	public List<Application> getApplicationByJob(@PathVariable Long jobId, Authentication authentication){
		return
				applicationService.getApplicationsByJob(jobId, authentication.getName());
	}

}
