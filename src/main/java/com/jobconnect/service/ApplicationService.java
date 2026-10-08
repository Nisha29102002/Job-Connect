package com.jobconnect.service;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.jobconnect.entity.Application;
import com.jobconnect.entity.User;
import com.jobconnect.repository.ApplicationRepository;
import org.springframework.web.server.ResponseStatusException;
import com.jobconnect.repository.UserRepository;
import com.jobconnect.repository.JobRepository;
import com.jobconnect.service.EmailService;

@Service
public class ApplicationService {
	private final ApplicationRepository applicationRepository;
	private final UserRepository userRepository;
	private final JobRepository jobRepository;
	private final EmailService emailService;
	
	public ApplicationService(ApplicationRepository applicationRepository, UserRepository userRepository, JobRepository jobRepository, EmailService emailService) {
		this.applicationRepository = applicationRepository;
		this.userRepository = userRepository;
		this.jobRepository = jobRepository;
		this.emailService = emailService;
	}
	
	public Application createApplication(Application application) {
		boolean alreadyApplied = applicationRepository.existsByApplicantIdAndJobId(application.getApplicant().getId(), application.getJob().getId());
		if(alreadyApplied) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "You have already applied for this job");
		}
		
		Application savedApplication = applicationRepository.save(application);
		emailService.sendApplicationEmail(application.getJob().getRecruiter().getEmail(), application.getApplicant().getName(), application.getJob().getTitle());
		return savedApplication;
	}
	
	public List<Application> getApplicationsByApplicant(Long applicantId){
		return applicationRepository.findByApplicantId(applicantId);
	}
	
	public List<Application> getApplicationsByJob(Long jobId, String recruiterEmail){
		var job = jobRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Job not found"));
		
		User recruiter = userRepository.findByEmail(recruiterEmail).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Recruiter not found"));
		if(job.getRecruiter() == null || !job.getRecruiter().getId().equals(recruiter.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can view applications only for your own jobs");
		}
		return applicationRepository.findByJobId(jobId);
	}
	
	public Application updateStatus(Long applicationId, String status, String recruiterEmail) {
		Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Application not found"));
				
				User recruiter = userRepository.findByEmail(recruiterEmail).orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Recruiter not found"));
				
				if(! application.getJob().getRecruiter().getId().equals(recruiter.getId())) {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You can update only applications for your own jobs");
				}
				application.setStatus(status);
				
				return 
						applicationRepository.save(application);
						
	}
	

}
