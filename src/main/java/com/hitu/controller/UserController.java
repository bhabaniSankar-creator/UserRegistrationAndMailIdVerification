package com.hitu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hitu.dto.UserDto;
import com.hitu.security.JwtUtils;
import com.hitu.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/register")
	public String registerUser(@RequestBody UserDto userDto) {
		return userService.saveUser(userDto);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDto userDto) {
		UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(userDto.getEmail(),
				userDto.getPassword());

		Authentication authenticate = authManager.authenticate(token);
		boolean status = authenticate.isAuthenticated();
		if (status) {
			return new ResponseEntity<>(jwtUtils.generateToken(userDto.getEmail()), HttpStatus.OK);
		}
		return new ResponseEntity<>("Invalid Credentials", HttpStatus.BAD_REQUEST);
	}
	 
	@PutMapping("/update")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto){
		 UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 String loggedInUserEmail = userDetails.getUsername();
		return new ResponseEntity<>(userService.updateUser(loggedInUserEmail, userDto), HttpStatus.OK);  
	}
	
	@PostMapping("/verify")
	public String verifyEmail(@RequestParam String email) {
		return userService.verifyEmail(email);
	}
	
	
	
}
