package com.example.api2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.api2.model.Gift;

@Repository
public interface GiftRepository extends MongoRepository<Gift, String> {
    // Custom query methods (if needed) can go here
	 Gift findByName(String name);
}
