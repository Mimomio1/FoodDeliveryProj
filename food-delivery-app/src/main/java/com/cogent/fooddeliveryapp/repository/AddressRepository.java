package com.cogent.fooddeliveryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cogent.fooddeliveryapp.dto.Address;


public interface AddressRepository extends JpaRepository<Address, Long> {

}
