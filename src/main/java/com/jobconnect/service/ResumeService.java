package com.jobconnect.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import com.jobconnect.entity.Resume;
import com.jobconnect.entity.User;
import com.jobconnect.repository.ResumeRepository;
import com.jobconnect.repository.UserRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;


@Service
public class ResumeService {
	private final ResumeRepository resumeRepository;
	private final UserRepository userRepository;
	public ResumeService(ResumeRepository resumeRepository, UserRepository userRepository) {
		this.resumeRepository = resumeRepository;
		this.userRepository = userRepository;
	}
	
	public Resume uploadResume(MultipartFile file, String email) {
		if(file.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Resume file is required");
		}
		
		if(! file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only PDF files are allowed");
		}
		
		User user = userRepository.findByEmail(email).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
		
		try {
			Path uploadDirectory = Paths.get("uploads");
			Files.createDirectories(uploadDirectory);
			
			String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
			Path filePath = uploadDirectory.resolve(fileName);
			Files.write(filePath, file.getBytes());
			Resume resume = resumeRepository.findByUserId(user.getId()).orElse(new Resume());
			resume.setFileName(file.getOriginalFilename());
			resume.setFilePath(filePath.toString());
			resume.setUser(user);
			return resumeRepository.save(resume);
			
		}catch(IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Could not save resume");
		}
		
	}
	
	public Resume getMyResume(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
		
		return resumeRepository.findByUserId(user.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resume not found"));
	}
	
	public Resource downloadMyResume(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
		Resume resume = resumeRepository.findByUserId(user.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resume not found"));
		
		Resource resource = new FileSystemResource(resume.getFilePath());
		if(!resource.exists()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Resume file not found");
		}
		
		return resource;
	}
	
	public void deleteMyResume(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
		
		Resume resume = resumeRepository.findByUserId(user.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resume not found"));
		
		try {
			Files.deleteIfExists(Path.of(resume.getFilePath()));
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Could not delete resume file");
		}
		
		resumeRepository.delete(resume);
	}
	

}
