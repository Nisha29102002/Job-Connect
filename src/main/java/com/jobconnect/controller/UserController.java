package com.jobconnect.controller;
import org.springframework.web.bind.annotation.RestController;
import com.jobconnect.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.jobconnect.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.jobconnect.dto.UserRegistrationDto;
import jakarta.validation.Valid;
import com.jobconnect.dto.UserLoginDto;
import com.jobconnect.dto.LoginResponseDto;


@RestController
public class UserController {
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	@PostMapping("/users")
	public User createUser(@Valid @RequestBody UserRegistrationDto dto) {
		return userService.registerUser(dto);
	}
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}
	@PutMapping("/users/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User user) {
		return userService.updateUser(id, user);
	}
	@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return "User deleted successfully";
	}
	@PostMapping("/login")
	public LoginResponseDto loginUser(@Valid @RequestBody UserLoginDto dto) {
		return userService.loginUser(dto);
	}

}
