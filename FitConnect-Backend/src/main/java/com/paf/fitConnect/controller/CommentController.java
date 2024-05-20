package com.paf.fitConnect.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.paf.fitConnect.DTO.CommentDTO;
import com.paf.fitConnect.modal.Comment;
import com.paf.fitConnect.service.CommentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsForPost(@PathVariable String postId) {
        List<Comment> comments = commentService.getCommentsForPost(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> addCommentToPost(@PathVariable String postId, @RequestBody CommentDTO request) {
        Comment comment = commentService.addCommentToPost(postId, request.getContent(), request.getCommentBy(), request.getCommentById() ,request.getCommentByProfile());
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String postId, @PathVariable String commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> editComment(@PathVariable String commentId, @RequestBody CommentDTO request) {
        Comment editedComment = commentService.editComment(commentId, request.getContent());
        if (editedComment != null) {
            return new ResponseEntity<>(editedComment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
