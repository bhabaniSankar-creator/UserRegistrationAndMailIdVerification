package com.hitu.service;

import com.hitu.dto.UserDto;

public interface UserService {
	public String saveUser(UserDto userDto);
	
	public String verifyEmail(String email);
	
	public UserDto updateUser(String email,UserDto userDto);
	
	public String deleteUser(Integer userId);
	
	public String deleteAllUsers();
	
	public UserDto updateUser(Integer userId,UserDto userDto);
	
	public UserDto getUser(Integer userId);
}
