package com.paf.fitConnect.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.paf.fitConnect.modal.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Post findPostById(String id);

    List<Post> findByUserId(String userId);
}
