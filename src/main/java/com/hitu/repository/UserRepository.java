package com.hitu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitu.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	public Optional<UserEntity> findByEmail(String email);
	
	public Optional<UserEntity> findByUserId(Integer userId);
}
