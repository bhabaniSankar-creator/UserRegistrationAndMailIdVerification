package com.hitu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String userFirstName;
	private String userMiddleName;
	private String userLastName;
	private Long phoneNo;
	private String email;
	private String address;
	
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean verify;
}
