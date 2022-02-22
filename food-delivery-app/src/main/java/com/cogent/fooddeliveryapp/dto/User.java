package com.cogent.fooddeliveryapp.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.apache.logging.log4j.util.PropertySource.Comparator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = {"addresses","role"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = {"addresses", "roles"})
@Table(name = "user_tbl", 
				uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User extends Comparator{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String email;

	private String password;

	private String name;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate doj = LocalDate.now();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Address> addresses;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "id"))
	public Set<Role> roles = new HashSet<>();
}
