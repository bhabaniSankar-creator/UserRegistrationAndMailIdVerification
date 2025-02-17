package com.hitu.service;

import com.hitu.dto.UserDto;

public interface UserService {
	public String saveUser(UserDto userDto);
	
	public String verifyEmail(String email);
}
