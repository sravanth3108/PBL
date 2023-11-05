package com.project.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.Model.PostProjectsModel;
import com.project.Model.Requests;

@Repository
public interface PostProjectsRepository extends MongoRepository<PostProjectsModel, String> {
	
	   @Query("{ 'keywords' : { $regex: ?0, $options: 'i' } }")
	    List<PostProjectsModel> findByKeywordsContaining(String keyword);
	   

	   @Query("{ 'projName' : { $regex: ?0, $options: 'i' } }")
	    List<PostProjectsModel> findByNameContaining(String name);

	
	
	

}