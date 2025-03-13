package com.hitu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
	private String userFirstName;
	private String userMiddleName;
	private String userLastName;
	private String password;
	private Long phoneNo;
	private String email;
	private String address;
	private String role;
}
