package com.project.Repository;

import com.project.Model.Bearcat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BearcatRepository extends MongoRepository<Bearcat, String> {
    Bearcat findByMail(String mail);
}