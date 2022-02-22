package com.cogent.fooddeliveryapp.payload.response;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


import com.cogent.fooddeliveryapp.dto.Role;
import com.cogent.fooddeliveryapp.payload.request.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	@NotBlank
	private String email;
	@NotBlank
	private String name;
	
	private Set<Address> address;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate doj;
	@NotEmpty
	public Set<String> roles;
}
