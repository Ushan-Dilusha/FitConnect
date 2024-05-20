package com.paf.fitConnect.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.paf.fitConnect.DTO.PostDTO;
import com.paf.fitConnect.modal.Post;
import com.paf.fitConnect.modal.User;
import com.paf.fitConnect.repository.PostRepository;
import com.paf.fitConnect.repository.UserRepository;
import com.paf.fitConnect.service.CommentService;
import com.paf.fitConnect.service.PostService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;

    @Override
    public List<Post> getAllPosts() {
        try {
            List<Post> posts = postRepository.findAll();
            for (Post post : posts) {
                post.setComments(commentService.getCommentsForPost(post.getId()));
            }
            posts.sort(Comparator.comparing(Post::getDate).reversed());
            return posts;
        } catch (DataAccessException e) {
            logger.error("Error fetching all posts", e);
            throw e;
        }
    }

    @Override
    public Optional<Post> getPostById(String id) {
        try {
            return postRepository.findById(id);
        } catch (DataAccessException e) {
            logger.error("Error fetching post by id: {}", id, e);
            throw e;
        }
    }

    @Override
    public Post createPost(Post post) {
        try {
            post.setDate(String.valueOf(new Date()));
            return postRepository.save(post);
        } catch (DataAccessException e) {
            logger.error("Error creating post", e);
            throw e;
        }
    }

    @Override
    public ResponseEntity<Post> editPost(PostDTO postDTO) {
        try {
            Post post = postRepository.findById(postDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Post not found"));

            post.setTitle(postDTO.getTitle());
            List<String> images = postDTO.getImages();
            post.setImages(images != null && !images.isEmpty() ? images : Collections.emptyList());
            post.setDescription(postDTO.getDescription());
            post.setDate(String.valueOf(new Date()));
            post.setVideo(postDTO.getVideo());
            postRepository.save(post);

            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (DataAccessException | IllegalArgumentException e) {
            logger.error("Error editing post with id: {}", postDTO.getId(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deletePost(String id) {
        try {
            postRepository.deleteById(id);
        } catch (DataAccessException e) {
            logger.error("Error deleting post with id: {}", id, e);
            throw e;
        }
    }

    @Override
    public ResponseEntity<Object> likePost(String postId, String userId) {
        try {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

            if (post.getLikedBy() == null) {
                post.setLikedBy(new ArrayList<>());
            }

            if (post.getLikedBy().contains(userId)) {
                post.getLikedBy().remove(userId);
                post.setLikeCount(post.getLikeCount() - 1);
            } else {
                post.getLikedBy().add(userId);
                post.setLikeCount(post.getLikeCount() + 1);
            }
            postRepository.save(post);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (DataAccessException | IllegalArgumentException e) {
            logger.error("Error liking post with id: {}", postId, e);
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Post> getPostByIdUserId(String userId) {
        try {
            List<Post> posts = postRepository.findByUserId(userId);
            for (Post post : posts) {
                post.setComments(commentService.getCommentsForPost(post.getId()));
            }
            return posts;
        } catch (DataAccessException e) {
            logger.error("Error fetching posts by userId: {}", userId, e);
            throw e;
        }
    }

}

