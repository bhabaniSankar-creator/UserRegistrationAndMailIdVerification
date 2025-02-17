package com.hitu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hitu.dto.UserDto;
import com.hitu.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public String registerUser(@RequestBody UserDto userDto) {
		return userService.saveUser(userDto);
	}
	
	@PostMapping("/verify")
	public String verifyEmail(@RequestParam String email) {
		return userService.verifyEmail(email);
	}
}
