package com.cogent.fooddeliveryapp.payload.request;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
	
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotBlank
	private String name;
	@NotEmpty
	private Set<Address> address;
	@JsonFormat(pattern = "MM-dd-yyyy")
	private LocalDate doj;
	@NotEmpty
	private Set<String> roles;
	// User Role
	// Admin Role
	// Role Enum
	// FoodType Enum
	// role DTO : role Id and RoleName(enum)
	// repo --> role
}
