package com.project.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.Model.Comments;
import com.project.Model.PostProjectsModel;
import com.project.Model.Subcomments;
import com.project.Repository.CommentRepository;
import com.project.Repository.PostProjectsRepository;
import com.project.Repository.SubcommentRepository;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentRepository commentRepository;

    private PostProjectsRepository projectRepo;
    
    private SubcommentRepository subCommentRepo;

    @Autowired
    public CommentController(CommentRepository commentRepository,PostProjectsRepository projectRepo,SubcommentRepository subCommentRepo) {
        this.commentRepository = commentRepository;
        this.projectRepo = projectRepo;
        this.subCommentRepo = subCommentRepo;
    }

	

    @PostMapping
    	public Comments addComment(@RequestBody Comments comment) {
            // Save the main comment to the database
            Comments savedComment = commentRepository.save(comment);
            return savedComment;
        }
    
    @PostMapping("/{commentId}/flag")
    public ResponseEntity<Void> flagComment(@PathVariable String commentId) {      
        Comments comment = commentRepository.findById(commentId).orElse(null);     
        comment.setFlags(comment.getFlags() + 1);
        commentRepository.save(comment);

        return ResponseEntity.ok().build();
    }
   
    @PostMapping("/{commentId}/replies")
    public Comments addReply(@PathVariable String commentId, @RequestBody Subcomments subcomment) {
        subcomment.setParentCommentId(commentId);
        Subcomments savedSubcomment = subCommentRepo.save(subcomment);
        Comments parentComment = commentRepository.findById(commentId).orElse(null);        
        if (parentComment != null) {
            List<Subcomments> subCommentsList = parentComment.getReplies();            
            if (subCommentsList == null) {
                subCommentsList = new ArrayList<>();
            }
            subCommentsList.add(savedSubcomment);

            parentComment.setReplies(subCommentsList);

            commentRepository.save(parentComment);
        }
        return commentRepository.findById(commentId).orElse(null);
    }
    @PostMapping("/{commentId}/subcomments/{subcommentId}/flag")
    public ResponseEntity<Void> flagSubComment(@PathVariable String commentId, @PathVariable String subcommentId){        
      
        Subcomments subcomment = subCommentRepo.findById(subcommentId).orElse(null);     
        subcomment.setFlags(subcomment.getFlags() + 1);
        Subcomments savedSubcomment = subCommentRepo.save(subcomment);

        Comments parentComment = commentRepository.findById(commentId).orElse(null);
        if (parentComment != null) {
            List<Subcomments> subCommentsList = parentComment.getReplies();            
            if (subCommentsList == null) {
                subCommentsList = new ArrayList<>();
            }
            subCommentsList.add(savedSubcomment);

            parentComment.setReplies(subCommentsList);
        commentRepository.save(parentComment);
        }

        return ResponseEntity.ok().build();
      
    }
   
}



    
