package com.paf.fitConnect.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paf.fitConnect.DTO.PostDTO;
import com.paf.fitConnect.modal.Post;
import com.paf.fitConnect.service.PostService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable String id) {
        Optional<Post> post = postService.getPostById(id);
        return post.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post savedPost = postService.createPost(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Post> updatePost(@RequestBody PostDTO postDTO) {
        return postService.editPost(postDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/like")
    public ResponseEntity<Object> likePost(@RequestParam String postId, 
                                                @RequestParam String userId) {
        return postService.likePost(postId,userId);
    }

    @GetMapping("user/{userId}")
    public List<Post> getPostByIdUserId(@PathVariable String userId) {
       return postService.getPostByIdUserId(userId);
    }
}
