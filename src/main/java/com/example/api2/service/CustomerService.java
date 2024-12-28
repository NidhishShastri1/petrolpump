package com.example.api2.service;

import org.springframework.stereotype.Service;

import com.example.api2.model.Customer;
import com.example.api2.repository.CustomerRepository;

import java.util.Random;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final Random random = new Random();

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    

    // Method to check if a customer already exists based on customerName and mobileNumber
    public boolean customerExists(String customerName, String mobileNumber) {
        // Check if a customer with the same name and mobile number exists
        return customerRepository.findByCustomerNameAndMobileNumber(customerName, mobileNumber) != null;
    }

    // Method to fetch a customer by mobile number
    public Customer getCustomerByMobileNumber(String mobileNumber) {
        return customerRepository.findByMobileNumber(mobileNumber);
    }

    // Method to add a new customer if they don't already exist
    public Customer addCustomer(String customerName, String mobileNumber) {
        // Check if the customer already exists
        if (customerExists(customerName, mobileNumber)) {
            // If the customer exists, return null or throw an exception as needed
            return null; // Or you can throw an exception like DuplicateCustomerException
        }
        
        
        

        // Generate Customer ID and Card Number
        String customerId = generateCustomerId();
        String cardNumber = generateCardNumber();

        // Create and save the customer
        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        customer.setMobileNumber(mobileNumber);
        customer.setCustomerId(customerId);
        customer.setCardNumber(cardNumber);

        return customerRepository.save(customer);
    }

    private String generateCustomerId() {
        // Generate a 4-digit random number
        int randomNumber = 1000 + random.nextInt(9000); // Ensures a 4-digit number
        return "CUST" + randomNumber; // e.g., CUST1234
    }

    private String generateCardNumber() {
        // Generate a 4-digit random number for the card number
        int randomNumber = 1000 + random.nextInt(9000); // Ensures a 4-digit number
        return "CARD" + randomNumber; // e.g., CARD1234
    }
}
