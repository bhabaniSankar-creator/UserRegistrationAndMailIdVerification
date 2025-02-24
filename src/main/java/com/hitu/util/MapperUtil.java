package com.hitu.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hitu.dto.UserDto;
import com.hitu.entity.UserEntity;

@Component
public class MapperUtil {
	
	@Autowired
	private ModelMapper modelmapper;
	
	public UserEntity mapToUserEntity(UserDto userDto) {
		return modelmapper.map(userDto, UserEntity.class);
	}
	
	public UserDto mapToUserDto(UserEntity userEntity) {
		return modelmapper.map(userEntity, UserDto.class);
	}
}
