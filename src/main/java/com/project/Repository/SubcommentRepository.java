package com.project.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.Model.Comments;

import com.project.Model.Subcomments;

@Repository
public interface SubcommentRepository extends MongoRepository<Subcomments, String> {

}
