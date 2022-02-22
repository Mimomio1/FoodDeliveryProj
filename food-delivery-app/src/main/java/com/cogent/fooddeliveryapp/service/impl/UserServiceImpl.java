package com.cogent.fooddeliveryapp.service.impl;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cogent.fooddeliveryapp.dto.User;
import com.cogent.fooddeliveryapp.repository.UserRepository;
import com.cogent.fooddeliveryapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

@Autowired
	
	UserRepository userRepository;
	@Override
	public User addUser(User user) {

		return userRepository.save(user);
	}

	@Override
	public Optional<User> getUserById(long id) {

		return userRepository.findById(id);
	}

	@Override
	public List<User> getAllUsers() {

		return userRepository.findAll();
	}

	@Override
	public String deleteUserById(long id) {

		userRepository.deleteById(id);
		return "success";
	}

	@Override
	public User updateUser(User user) {

		return null;
	}

	@Override
	public List<User> getAllUsersAscOrder() {

		return null;
	}

	@Override
	public List<User> getAllUsersDescOrder() {

		return null;
	}

	@Override
	public boolean existsById(long id) {

		return userRepository.existsById(id);
	}

}
