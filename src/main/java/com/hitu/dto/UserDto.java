package com.hitu.dto;

import lombok.Data;

@Data
public class UserDto {
	private String userFirstName;
	private String userMiddleName;
	private String userLastName;
	private Long phoneNo;
	private String email;
	private String address;
}
