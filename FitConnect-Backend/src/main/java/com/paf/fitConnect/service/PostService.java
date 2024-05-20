package com.paf.fitConnect.service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import com.paf.fitConnect.DTO.PostDTO;
import com.paf.fitConnect.modal.Post;

public interface PostService {

    List<Post> getAllPosts();

    Optional<Post> getPostById(String id);

    Post createPost(Post post);

    ResponseEntity<Post> editPost( PostDTO postDTO);

    void deletePost(String id);

    ResponseEntity<Object> likePost(String postId, String userId);

    List<Post> getPostByIdUserId(String userId);

}
