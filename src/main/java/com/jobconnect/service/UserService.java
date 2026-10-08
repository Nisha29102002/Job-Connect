package com.jobconnect.service;
import org.springframework.stereotype.Service;
import com.jobconnect.repository.UserRepository;
import com.jobconnect.entity.User;
import java.util.List;
import com.jobconnect.dto.UserRegistrationDto;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.jobconnect.dto.UserLoginDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.jobconnect.security.JwtService;
import com.jobconnect.dto.LoginResponseDto;

@Service
public class UserService {
	
	private PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final JwtService jwtService;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}
	public User saveUser(User user) {
		return
				userRepository.save(user);
	}
	public User registerUser(UserRegistrationDto dto) {
		
		if (userRepository.existsByEmail(dto.getEmail())) {
			throw new ResponseStatusException(
					HttpStatus.CONFLICT, 
					"Email already registered"
					);
		}
		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(dto.getRole());
		
		return userRepository.save(user);
	}
	public LoginResponseDto loginUser(UserLoginDto dto) {
		System.out.println("LOGIN METHOD CALLED");
		User user = userRepository.findByEmail(dto.getEmail())
				.orElse(null);
		
		if (user == null) {
			throw new
			ResponseStatusException(
					HttpStatus.UNAUTHORIZED,
					"Invalid email or password");
		}
		if(! passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new
			ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
		}
		
		String token = jwtService.generateToken(user.getEmail(), user.getRole());
		return new LoginResponseDto(token);
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	public User getUserById(Long id) {
		return
				userRepository.findById(id).orElse(null);
	}
	public User updateUser(Long id, User user) {
		User existingUser = userRepository.findById(id).orElse(null);
		
		if (existingUser == null) {
			return null;
		}
		
		existingUser.setName(user.getName());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(user.getPassword());
		existingUser.setRole(user.getRole());
		
		return
				userRepository.save(existingUser);
	}
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
	
	

}
