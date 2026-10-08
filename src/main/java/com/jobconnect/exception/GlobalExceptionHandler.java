package com.jobconnect.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleValidationErrors(
			MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->
		errors.put(error.getField(),error.getDefaultMessage()));
		
		return new ErrorResponse(400,"Validation failed", errors);
	}
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<Map<String,String>> handleResponseStatusException(ResponseStatusException ex){
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getReason());
		
		return
				ResponseEntity.status(ex.getStatusCode()).body(error);
	}
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String,String>>handleRuntimeException(RuntimeException ex){
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getMessage());
		
		return ResponseEntity.status(500).body(error);
	}
	
	
	

}
