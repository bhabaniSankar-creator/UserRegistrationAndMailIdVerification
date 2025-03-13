package com.hitu.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hitu.dto.UserDto;
import com.hitu.entity.UserEntity;
import com.hitu.entity.UserEntity.Role;
import com.hitu.repository.UserRepository;
import com.hitu.util.MailUtil;
import com.hitu.util.MapperUtil;


@Service
public class UserServiceImpl implements UserService,UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private MapperUtil mapper;
	
	@Autowired
	private MailUtil mail;
	
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	 
	
	@Override
	public String saveUser(UserDto userDto) {
		userDto.setPassword(pwdEncoder.encode(userDto.getPassword()));
		UserEntity userEntity = mapper.mapToUserEntity(userDto);
		userEntity.setRole(Role.valueOf(userDto.getRole()));
		UserEntity savedUser = userRepo.save(userEntity);
		/*
		 * String subject="Verify Account | Bhabani Pvt Ltd";
		 * 
		 * StringBuffer body=new StringBuffer();
		 * 
		 * body.append("<h1> \"Hello "+savedUser.getUserFirstName()
		 * +"Use below Link to verify email</h1>"); body.append("</br>");
		 * body.append("<a href=\"http://localhost:8080/api/verify?email="+savedUser.
		 * getEmail()+"\">Verify Email</a>");
		 * 
		 * boolean sendEmail = mail.sendEmail(subject, body.toString(),
		 * savedUser.getEmail()); if (sendEmail==true) { return
		 * "Registration successful . Check your mail "; }
		 */
		if (savedUser!=null) {
			return "Registration successful";
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

	@Override
	public UserDto updateUser(String emailId,UserDto userDto) {
		Optional<UserEntity> optional = userRepo.findByEmail(emailId);
		if (optional.isPresent()) {
			UserEntity existingUser = optional.get();
			if (userDto.getUserFirstName()!=null) {
				existingUser.setUserFirstName(userDto.getUserFirstName());
			}
			if (userDto.getUserMiddleName()!=null) {
				existingUser.setUserMiddleName(userDto.getUserMiddleName());
			}
			if (userDto.getUserLastName()!=null) {
				existingUser.setUserLastName(userDto.getUserLastName());
			}
			if (userDto.getEmail()!=null) {
				existingUser.setEmail(userDto.getEmail());
			}
			if (userDto.getAddress()!=null) {
				existingUser.setAddress(userDto.getAddress());
			}
			if (userDto.getPhoneNo()!=null) {
				existingUser.setPhoneNo(userDto.getPhoneNo());
			}
			if (userDto.getRole() != null) {

				existingUser.setRole(Role.valueOf(userDto.getRole()));
			}
			return mapper.mapToUserDto(userRepo.save(existingUser));
		}
		return null;
	}

	

	@Override
	public String deleteUser(Integer userId) {
		if (userRepo.existsById(userId)) {
			userRepo.deleteById(userId);
			return "User deleted Successfully";
		}
		return "User id not found";
		
	}

	@Override
	public String deleteAllUsers() {
		if (userRepo.count()>0) {
			userRepo.deleteAll();
			return "All Users deleted Successfully";
		}
		return "Can not perform the operation . Database is empty";
		
	}

	@Override
	public UserDto updateUser(Integer userId,UserDto userDto) {
		Optional<UserEntity> optional = userRepo.findById(userId);
		if (optional.isPresent()) {
			UserEntity existingUser = optional.get();
			if (userDto.getUserFirstName()!=null) {
				existingUser.setUserFirstName(userDto.getUserFirstName());
			}
			if (userDto.getUserMiddleName()!=null) {
				existingUser.setUserMiddleName(userDto.getUserMiddleName());
			}
			if (userDto.getUserLastName()!=null) {
				existingUser.setUserLastName(userDto.getUserLastName());
			}
			if (userDto.getEmail()!=null) {
				existingUser.setEmail(userDto.getEmail());
			}
			if (userDto.getAddress()!=null) {
				existingUser.setAddress(userDto.getAddress());
			}
			if (userDto.getPhoneNo()!=null) {
				existingUser.setPhoneNo(userDto.getPhoneNo());
			}
			if (userDto.getRole() != null) {
				existingUser.setRole(Role.valueOf(userDto.getRole()));
			}
			return mapper.mapToUserDto(userRepo.save(existingUser));
			
		}
		return null;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserEntity> optional = userRepo.findByEmail(email);
		if (optional.isPresent()) {
			UserEntity user = optional.get();
			return new User(user.getEmail(), user.getPassword(), getAuthorities(user.getRole()));
		}
		return null;
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Role role){
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" +role.name()));
		return authorities;
	}

	@Override
	public UserDto getUser(Integer userId) {
		Optional<UserEntity> optional = userRepo.findById(userId);
		if (optional.isPresent()) {
			return mapper.mapToUserDto(optional.get());
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	

}
