package com.cogent.fooddeliveryapp.controller;

import java.util.*;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.fooddeliveryapp.IdNotFoundException;
import com.cogent.fooddeliveryapp.dto.Address;
import com.cogent.fooddeliveryapp.dto.Role;
import com.cogent.fooddeliveryapp.dto.User;
import com.cogent.fooddeliveryapp.enums.ERole;
import com.cogent.fooddeliveryapp.payload.request.SignUpRequest;
import com.cogent.fooddeliveryapp.payload.response.UserResponse;
import com.cogent.fooddeliveryapp.repository.RoleRepository;
import com.cogent.fooddeliveryapp.service.UserService;
import com.example.demo.exception.NoDataFoundException;



@RestController

@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteByUserId(@PathVariable("id") Long id)
	{
		if (userService.existsById(id)) {
			userService.deleteUserById(id);
			return ResponseEntity.noContent().build();
		}
		else {
			throw new NoDataFoundException("record not found");
		}
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id")long id) {
		
	User user =	userService.getUserById(id).orElseThrow(()->new NoDataFoundException("data not available"));
		// DTO ===> UserResponse()
	UserResponse userResponse=  new UserResponse();
	userResponse.setEmail(user.getEmail());
	userResponse.setName(user.getName());
	Set<String> roles= new HashSet<>();
	userResponse.setDoj(user.getDoj());
	user.getRoles().forEach(e2->{
		roles.add(e2.getRoleName().name());
	});
	Set<com.cogent.fooddeliveryapp.payload.request.Address> addresses = new HashSet<>();
	user.getAddresses().forEach(e3->{
		com.cogent.fooddeliveryapp.payload.request.Address address2 = new com.cogent.fooddeliveryapp.payload.request.Address();
		address2.setHouseNumber(e3.getHouseNumber());
		address2.setCity(e3.getCity());
		address2.setCountry(e3.getCountry());
		address2.setState(e3.getState());
		address2.setStreet(e3.getStreet());
		address2.setZip(e3.getZip());
		addresses.add(address2);
	});
	userResponse.setAddress(addresses);
	userResponse.setRoles(roles);
	return ResponseEntity.status(200).body(userResponse);
	}

	
	@GetMapping("/api/users")
	public ResponseEntity<?> getAllUsers() {
		
		List<User> list = userService.getAllUsers();
		List<UserResponse> userResponses = new ArrayList<>();
		list.forEach(e->{
			UserResponse userResponse = new UserResponse();
			userResponse.setEmail(e.getEmail());
			userResponse.setName(e.getName());
			Set<String> roles = new HashSet<>();
			userResponse.setDoj(e.getDoj());
			e.getRoles().forEach(e2->{
				roles.add(e2.getRoleName().name());
			});
			Set<com.cogent.fooddeliveryapp.payload.request.Address> addresses = new HashSet<>();
			e.getAddresses().forEach(e3->{
				com.cogent.fooddeliveryapp.payload.request.Address address2 = new com.cogent.fooddeliveryapp.payload.request.Address();
				address2.setHouseNumber(e3.getHouseNumber());
				address2.setCity(e3.getCity());
				address2.setCountry(e3.getCountry());
				address2.setState(e3.getState());
				address2.setStreet(e3.getStreet());
				address2.setZip(e3.getZip());
				addresses.add(address2);
			});
			userResponse.setAddress(addresses);
			userResponse.setRoles(roles);
			userResponses.add(userResponse);
		});
		if (userResponses.size()>0) {
			return ResponseEntity.ok(userResponses);
		}
		else {
			throw new NoDataFoundException("no users are there");
		}
		
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody SignUpRequest signUpRequest){
		
		Set<Role>roles = new HashSet<>();
		if(signUpRequest.getRoles()==null)
		{
			Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER).orElseThrow(()-> 
			new IdNotFoundException("RoleId not found exception"));
			roles.add(userRole);
		}
		else {
		signUpRequest.getRoles().forEach(e->{
				switch(e)
				{
				case "user":
					Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER).orElseThrow(()-> 
					new IdNotFoundException("RoleId not found exception"));
					roles.add(userRole);
					break;
				case "admin":
					Role userAdmin = roleRepository.findByRoleName(ERole.ROLE_USER).orElseThrow(()-> 
					new IdNotFoundException("RoleId not found exception"));
					roles.add(userAdmin);
				default:
					break;
				}
			});
		}
		User user = new User();
		
		Set<Address> addresses = new HashSet<>();
		signUpRequest.getAddress().forEach(e->{
			Address address = new Address();
			address.setCity(e.getCity());
			address.setCountry(e.getCountry());
			address.setHouseNumber(e.getHouseNumber());
			address.setState(e.getState());
			address.setStreet(e.getStreet());
//			address.setUser(e.getUser());
			address.setZip(e.getZip());
			addresses.add(address);
		});
		user.setAddresses(addresses);
		user.setEmail(signUpRequest.getEmail());
		user.setName(signUpRequest.getName());
		user.setPassword(signUpRequest.getPassword());
		user.setRoles(roles);
		user.setDoj(signUpRequest.getDoj());
		
		User user2 = userService.addUser(user);
		
		return ResponseEntity.status(201).body(user2);
	}
	
	

}
