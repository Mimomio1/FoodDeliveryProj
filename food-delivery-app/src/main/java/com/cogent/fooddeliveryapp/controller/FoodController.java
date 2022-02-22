package com.cogent.fooddeliveryapp.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.fooddeliveryapp.dto.Food;
import com.cogent.fooddeliveryapp.repository.FoodRepository;
import com.example.demo.exception.NoDataFoundException;



@RestController
@RequestMapping("/food")
@Validated
public class FoodController {
	
	@Autowired
	FoodRepository foodRepository;
	
	@PostMapping(value = "/")
	public ResponseEntity<?> createFood(@Valid @RequestBody Food food) {
		
		Food food2 = foodRepository.save(food);
		
		return ResponseEntity.status(201).body(food2);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getFoodById(@PathVariable("id") @Min(1) long id) {
		
		
		Food food =foodRepository.findById(id).orElseThrow(()-> new NoDataFoundException("no data found"));
		
		return ResponseEntity.ok(food);
	}
	
	@GetMapping(value = "/")
	public ResponseEntity<List<Food>> getFoodItems()
	{
		List<Food> foodItems = foodRepository.findAll();
		
		return ResponseEntity.ok(foodItems);
	}
	
	@GetMapping(value = "/all/desc")
	public ResponseEntity<?> getAllDescOrder() {
		
		List<Food> foodList = foodRepository.findAll();
		
		Collections.sort(foodList, (a,b)-> b.getId().compareTo(a.getId()));
		return ResponseEntity.status(200).body(foodList);
	}
	
	@GetMapping(value = "/all/asc")
	public ResponseEntity<?> getAllAscOrder() {
		
		List<Food> foodList = foodRepository.findAll();
		
		Collections.sort(foodList, (a,b)-> a.getId().compareTo(b.getId()));
		return ResponseEntity.status(200).body(foodList);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFoodItemById(@PathVariable("id") Long id)
	{
		foodRepository.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
//	@PutMapping(value = "/{foodid}")
//	public ResponseEntity<?> updateFoodItem()
//	{
//		
//	}
}
