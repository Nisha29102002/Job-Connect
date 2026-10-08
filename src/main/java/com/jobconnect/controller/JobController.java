package com.jobconnect.controller;
import org.springframework.web.bind.annotation.RestController;
import com.jobconnect.service.JobService;
import org.springframework.web.bind.annotation.PostMapping;

import com.jobconnect.dto.JobRequestDto;
import com.jobconnect.entity.Job;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.validation.Valid;
import com.jobconnect.dto.JobRequestDto;
import com.jobconnect.dto.JobResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.jobconnect.dto.PageResponseDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;


@RestController
public class JobController {
	
	private JobService jobService;
	public JobController(JobService jobService) {
		this.jobService = jobService;
	}
	
	@PostMapping("/jobs")
	public JobResponseDto createJob(@Valid @RequestBody JobRequestDto jobRequestDto, Authentication authentication) {
		Job job = new Job();
		
		job.setTitle(jobRequestDto.getTitle());
		job.setLocation(jobRequestDto.getLocation());
		job.setDescription(jobRequestDto.getDescription());
		job.setEmploymentType(jobRequestDto.getEmploymentType());
		job.setSalary(jobRequestDto.getSalary());
		
		Job savedJob = jobService.saveJob(job, jobRequestDto.getCompanyId(), authentication.getName());
		
		return new JobResponseDto(
				savedJob.getId(),
				savedJob.getTitle(),
				savedJob.getCompany().getName(),
				savedJob.getLocation(),
				savedJob.getDescription(),
				
				savedJob.getEmploymentType(),
				savedJob.getSalary()
				);
	}
	
	@GetMapping("/jobs")
	public PageResponseDto getAllJobs(Pageable pageable){
		Page<Job> jobs = jobService.getJobs(pageable);
		
		List<JobResponseDto> content = jobs.map(job -> new JobResponseDto(
				job.getId(),
				job.getTitle(),
				job.getCompany().getName(),
				job.getLocation(),
				job.getDescription(),
				job.getEmploymentType(),
				job.getSalary()
				)).getContent();
		
		return new PageResponseDto(
				content,
				jobs.getNumber(),
				jobs.getSize(),
				jobs.getTotalElements(),
				jobs.getTotalPages());
				
	}
	
	@GetMapping("jobs/search")
	public List<JobResponseDto> searchJobs(@RequestParam String title){
		List<Job> jobs = jobService.searchJobsByTitle(title);
		return jobs.stream().map(job -> new JobResponseDto(
				job.getId(),
				job.getTitle(),
				job.getCompany().getName(),
				job.getLocation(),
				job.getDescription(),
				job.getEmploymentType(),
				job.getSalary())).toList();
	}
	
	@GetMapping("jobs/search/location")
	public List<JobResponseDto> searchJobsByLocation(@RequestParam String location){
		List<Job> jobs = jobService.searchJobsByLocation(location);
		return jobs.stream().map(job -> new JobResponseDto(
				job.getId(),
				job.getTitle(),
				job.getCompany().getName(),
				job.getLocation(),
				job.getDescription(),
				job.getEmploymentType(),
				job.getSalary())).toList();
	}
	@GetMapping("/jobs/{id}")
	public JobResponseDto getJobById(@PathVariable Long id) {
		Job job = jobService.getJobById(id);
		
		return new JobResponseDto(
				job.getId(),
				job.getTitle(),
				job.getCompany().getName(),
				job.getLocation(),
				job.getDescription(),
				job.getEmploymentType(),
				job.getSalary()
				);
				
	}
	
	@PutMapping("/jobs/{id}")
	public JobResponseDto updateJob(@PathVariable Long id,@Valid @RequestBody JobRequestDto jobRequestDto, Authentication authentication) {
		Job job = new Job();
		
		job.setTitle(jobRequestDto.getTitle());
		
		job.setLocation(jobRequestDto.getLocation());
		job.setDescription(jobRequestDto.getDescription());
		job.setEmploymentType(jobRequestDto.getEmploymentType());
		job.setSalary(jobRequestDto.getSalary());
		
		Job updatedJob = jobService.updateJob(id, job, jobRequestDto.getCompanyId(), authentication.getName());
		
		return new JobResponseDto(
				updatedJob.getId(),
				updatedJob.getTitle(),
				updatedJob.getCompany().getName(),
				updatedJob.getLocation(),
				updatedJob.getDescription(),
				updatedJob.getEmploymentType(),
				updatedJob.getSalary()
				);
	}
	
	@DeleteMapping("/jobs/{id}")
	public String deleteJob(@PathVariable Long id, Authentication authentication) {
		jobService.deleteJob(id, authentication.getName());
		
		return "Job deleted successfully";
	}
	
	@GetMapping("/recruiter/jobs")
	public List<JobResponseDto> getRecruiterjobs(Authentication authentication) {
		List<Job> jobs = jobService.getJobsByRecruiter(authentication.getName());
		return jobs.stream().map(job -> new JobResponseDto(
				job.getId(),
				job.getTitle(),
				job.getCompany().getName(),job.getLocation(),
				job.getDescription(),
				job.getEmploymentType(),
				job.getSalary())).toList();
	}

}
