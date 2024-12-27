package com.example.api2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api2.model.Customer;
import com.example.api2.repository.CustomerRepository;

import org.springframework.http.MediaType;  // Correct import for MediaType
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RedeemController {  // Capitalize class name

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/api/points")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping(value = "/api/add-points", consumes = "multipart/form-data") // Ensure it's expecting form data
    public ResponseEntity<?> addPoints(
        @RequestParam("customerId") String customerId,
        @RequestParam("points") int points
    ) {
    	System.out.println("Received customerId: " + customerId);
        // Find the customer by ID
        Optional<Customer> customerOpt = customerRepository.findBycustomerId(customerId);
        if (!customerOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }

        Customer customer = customerOpt.get();
        customer.setPoints(customer.getPoints() + points); // Ensure method name matches your Customer model
        customerRepository.save(customer); // Save updated customer

        return ResponseEntity.ok(customer); // Return updated customer
    }
}