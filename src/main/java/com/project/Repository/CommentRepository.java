package com.project.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.Model.Comments;


public interface CommentRepository extends MongoRepository<Comments, String> {
    List<Comments> findByProjectId(String projectId);
}

