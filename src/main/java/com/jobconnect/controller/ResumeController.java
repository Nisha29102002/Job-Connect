package com.jobconnect.controller;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.jobconnect.entity.Resume;
import com.jobconnect.service.ResumeService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestPart;


@RestController
public class ResumeController {
	private final ResumeService resumeService;
	public ResumeController(ResumeService resumeService) {
		this.resumeService = resumeService;
	}
	@PostMapping(
			value = "/resumes/upload", 
			consumes =
			MediaType.MULTIPART_FORM_DATA_VALUE
			)
	public Resume uploadResume(@RequestPart("file") MultipartFile file, Authentication authentication) {
		return resumeService.uploadResume(file, authentication.getName());
	}
	
	@GetMapping("/resumes/my")
	public Resume getMyResume(Authentication authentication) {
		return resumeService.getMyResume(authentication.getName());
	}
	
	@GetMapping("/resumes/download")
	public ResponseEntity<Resource> downloadMyResume(Authentication authentication){
		Resource resource = resumeService.downloadMyResume(authentication.getName());
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\"" + resource.getFilename() + "\"").contentType(MediaType.APPLICATION_PDF).body(resource);
	}
	
	@DeleteMapping("/resumes/my")
	public ResponseEntity<String> deleteMyResume(Authentication authentication){
		resumeService.deleteMyResume(authentication.getName());
		
		return ResponseEntity.ok("Resume deleted successfully");
	}
	
	

}
