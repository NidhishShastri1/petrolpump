package com.example.api2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.api2.model.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    // Custom query to find a customer by customerName and mobileNumber
    Customer findByCustomerNameAndMobileNumber(String customerName, String mobileNumber);

    // New custom query to find a customer by phone number
    Customer findByMobileNumber(String mobileNumber);
    Optional<Customer> findBycustomerId(String customerId);
}
