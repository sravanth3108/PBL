package com.project.Controller;

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

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentRepository commentRepository;

    private PostProjectsRepository projectRepo;

    @Autowired
    public CommentController(CommentRepository commentRepository,PostProjectsRepository projectRepo) {
        this.commentRepository = commentRepository;
        this.projectRepo = projectRepo;
    }

	

    @PostMapping
    public Comments addComment(@RequestBody Comments comment) {
        // Save the comment to the database
        return commentRepository.save(comment);
    }

    @PostMapping("/{commentId}/replies")
    public ResponseEntity<Comments> addReply(@PathVariable String commentId, @RequestBody Subcomments subcomment) {
        Optional<Comments> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comments comment = optionalComment.get();
            comment.getReplies().add(subcomment);
            Comments updatedComment = commentRepository.save(comment);
            return ResponseEntity.ok(updatedComment);
        } else {
            // Handle case when the comment with given ID does not exist
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/load/{projectName}")
    public ResponseEntity<List<Comments>> loadComments(@PathVariable String projectName) {
        Optional<PostProjectsModel> optionalProject = projectRepo.findById(projectName);
        if (optionalProject.isPresent()) {
            PostProjectsModel project = optionalProject.get();
            List<Comments> comments = commentRepository.findByProjectId(projectName);
            System.out.println("comments" + comments);
            return ResponseEntity.ok(comments);
        } else {
            // Handle case when the project with given name does not exist
            return ResponseEntity.notFound().build();
        }
    }

    

    @GetMapping("/{projectId}")
    public List<Comments> getCommentsByProjectId(@PathVariable String projectId) {
        return commentRepository.findByProjectId(projectId);
    }
}
