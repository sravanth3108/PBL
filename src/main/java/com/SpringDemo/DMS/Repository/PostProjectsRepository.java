package com.SpringDemo.DMS.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.SpringDemo.DMS.Model.PostProjects;

@Repository
public interface PostProjectsRepository extends MongoRepository<PostProjects, String> {

}
