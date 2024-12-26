package com.example.api2.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api2.model.Customer;
import com.example.api2.service.CustomerService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
  
   

  
    

    @GetMapping("/fetch-details")
    public ResponseEntity<Map<String, Object>> getCustomerDetails(
            @RequestParam("mobileNumber") String mobileNumber) {
        logger.info("Fetching customer details for mobileNumber: {}", mobileNumber);

        if (mobileNumber == null || mobileNumber.length() != 10) {
            return buildErrorResponse("Valid mobile number is required.", 400);
        }

        Customer customer = customerService.getCustomerByMobileNumber(mobileNumber);
        if (customer == null) {
            logger.warn("Customer not found for mobileNumber: {}", mobileNumber);
            return buildErrorResponse("Customer not found.", 404);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("customer", customer);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(response);

        
    }

    @PostMapping("/generate-customer-details")
    public ResponseEntity<Map<String, String>> generateCustomerDetails(
            @RequestParam("customerName") String customerName,
            @RequestParam("mobileNumber") String mobileNumber) {
        logger.info("Generating customer details for customerName: {}, mobileNumber: {}", customerName, mobileNumber);

       
        String customerId = "CUST" + (1000 + new Random().nextInt(9000));
        String cardNumber = "CARD" + (1000 + new Random().nextInt(9000));

        Map<String, String> response = new HashMap<>();
        response.put("customerId", customerId);
        response.put("cardNumber", cardNumber);
        response.put("status", "success");

        return buildSuccessResponse(response);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addCustomer(
            @RequestParam("customerName") String customerName,
            @RequestParam("mobileNumber") String mobileNumber) {
        logger.info("Adding customer with customerName: {}, mobileNumber: {}", customerName, mobileNumber);

      
        Customer customer = customerService.addCustomer(customerName, mobileNumber);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Customer saved successfully.");
        response.put("customerId", customer.getCustomerId());
        response.put("cardNumber", customer.getCardNumber());

        return buildSuccessResponse(response);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, int statusCode) {
        logger.error("Error response: {} (Status code: {})", message, statusCode);
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        errorResponse.put("message", message);

        return ResponseEntity.status(statusCode)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(errorResponse);
    }

    private <T> ResponseEntity<T> buildSuccessResponse(T body) {
        logger.info("Success response: {}", body);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(body);
    }
}


