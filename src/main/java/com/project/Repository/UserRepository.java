package com.project.Repository;

import com.project.Model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    // You can define custom query methods if needed
    // No need to define the save method, it's already provided by MongoRepository

	   User findByUsrName(String userName);
}
