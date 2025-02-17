package com.hitu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitu.dto.UserDto;
import com.hitu.entity.UserEntity;
import com.hitu.repository.UserRepository;
import com.hitu.util.MailUtil;
import com.hitu.util.MapperUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private MapperUtil mapper;
	
	@Autowired
	private MailUtil mail;
	
	@Override
	public String saveUser(UserDto userDto) {
		UserEntity userEntity = mapper.mapToUserEntity(userDto);
		UserEntity savedUser = userRepo.save(userEntity);
		String subject="Verify Account | Bhabani Pvt Ltd";
		
		StringBuffer body=new StringBuffer();
		
		body.append("<h1> \"Hello "+savedUser.getUserFirstName()+"Use below Link to verify email</h1>");
		body.append("</br>");
		body.append("<a href=\"http://localhost:8080/api/verify?email="+savedUser.getEmail()+"\">Verify Email</a>");
	
		boolean sendEmail = mail.sendEmail(subject, body.toString(), savedUser.getEmail());
		if (sendEmail==true) {
			return "Registration successful . Check your mail ";
		}
		return "Registration Failed";
	}

	@Override
	public String verifyEmail(String email) {
		Optional<UserEntity> optional = userRepo.findByEmail(email);
		if (optional.isPresent()) {
			UserEntity userEntity = optional.get();
			userEntity.setVerify(true);
			userRepo.save(userEntity);
			return "Email verified";
		}
		return "Email verification failed";
	}

}
