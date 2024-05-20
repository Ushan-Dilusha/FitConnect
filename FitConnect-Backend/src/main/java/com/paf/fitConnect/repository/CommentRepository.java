package com.paf.fitConnect.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.paf.fitConnect.modal.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostId(String postId);
}
