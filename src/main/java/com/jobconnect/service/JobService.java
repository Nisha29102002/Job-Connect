package com.jobconnect.service;
import org.springframework.stereotype.Service;
import com.jobconnect.repository.JobRepository;
import com.jobconnect.entity.Job;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.jobconnect.entity.Company;
import org.springframework.web.server.ResponseStatusException;
import com.jobconnect.repository.CompanyRepository;
import com.jobconnect.entity.User;
import com.jobconnect.repository.UserRepository;

@Service
public class JobService {
	private JobRepository jobRepository;
	private CompanyRepository companyRepository;
	private UserRepository userRepository;
	
	public JobService(JobRepository jobRepository, CompanyRepository companyRepository, UserRepository userRepository) {
		this.jobRepository = jobRepository;
		this.companyRepository = companyRepository;
		this.userRepository = userRepository;
	}
	
	public Job saveJob(Job job, Long companyId, String recruiterEmail) {
		Company company = companyRepository.findById(companyId).orElseThrow(()	-> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Company not found"));
				User recruiter = userRepository.findByEmail(recruiterEmail).orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Recruiter Not Found"));
						job.setCompany(company);
						job.setRecruiter(recruiter);
						return jobRepository.save(job);
				}
	
	public Page<Job> getJobs(Pageable pageable){
		return jobRepository.findAll(pageable);
	}
	
	public List<Job> searchJobsByTitle(String title){
		return
				jobRepository.findByTitleContainingIgnoreCase(title);
	}
	
	public List<Job> searchJobsByLocation(String location){
		return
				jobRepository.findByLocationContainingIgnoreCase(location);
	}
	public Job getJobById(Long id) {
		return jobRepository.findById(id).orElse(null);
	}
	
	public Job updateJob(Long id, Job job, Long companyId, String recruiterEmail) {
		Job existingJob = jobRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Job not found"));
		
		User recruiter = userRepository.findByEmail(recruiterEmail).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Recruiter not found"));
		
		if (!existingJob.getRecruiter().getId().equals(recruiter.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You can update only your own jobs");
			}
			
			Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Company not found"));
					existingJob.setTitle(job.getTitle());
					existingJob.setCompany(company);
			
			existingJob.setLocation(job.getLocation());
			
			existingJob.setDescription(job.getDescription());
			
			existingJob.setEmploymentType(job.getEmploymentType());
			
			existingJob.setSalary(job.getSalary());
			
			return jobRepository.save(existingJob);
		
	}
	
	public void deleteJob(Long id, String recruiterEmail) {
		Job existingJob = jobRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Job not found"));
		
		User recruiter = userRepository.findByEmail(recruiterEmail).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Recruiter not found"));
		
		if(!existingJob.getRecruiter().getId().equals(recruiter.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You can delete only your own jobs");
		}
		jobRepository.delete(existingJob);
				
	}
	
	public List<Job> getJobsByRecruiter(String recruiterEmail){
		User recruiter = userRepository.findByEmail(recruiterEmail)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Recruiter not found"));
						return jobRepository.findByRecruiterId(recruiter.getId());
						}
}
