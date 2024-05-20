package com.paf.fitConnect.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.paf.fitConnect.modal.Comment;
import com.paf.fitConnect.modal.Post;
import com.paf.fitConnect.repository.CommentRepository;
import com.paf.fitConnect.repository.PostRepository;
import com.paf.fitConnect.service.CommentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public List<Comment> getCommentsForPost(String postId) {
        try {
            return commentRepository.findByPostId(postId);
        } catch (DataAccessException e) {
            logger.error("Error fetching comments for postId: {}", postId, e);
            throw e;
        }
    }

    @Override
    public Comment addCommentToPost(String postId, String content, String commentBy, String commentById, String commentByProfile) {
        try {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setCommentBy(commentBy);
            comment.setCommentById(commentById);
            comment.setCommentByProfile(commentByProfile);
            comment.setCreatedAt(String.valueOf(new Date()));

            Post post = postRepository.findById(postId).orElse(null);
            if (post != null) {
                comment.setPostId(post.getId());
                return commentRepository.save(comment);
            } else {
                throw new IllegalArgumentException("Invalid postId: " + postId);
            }
        } catch (DataAccessException | IllegalArgumentException e) {
            logger.error("Error adding comment to postId: {}", postId, e);
            throw e;
        }
    }

    @Override
    public void deleteComment(String postId, String commentId) {
        try {
            commentRepository.deleteById(commentId);
        } catch (DataAccessException e) {
            logger.error("Error deleting comment with id: {}", commentId, e);
            throw e;
        }
    }

    @Override
    public Comment editComment(String commentId, String content) {
        try {
            Optional<Comment> optionalComment = commentRepository.findById(commentId);
            if (optionalComment.isPresent()) {
                Comment comment = optionalComment.get();
                comment.setContent(content);
                return commentRepository.save(comment);
            } else {
                throw new IllegalArgumentException("Invalid commentId: " + commentId);
            }
        } catch (DataAccessException | IllegalArgumentException e) {
            logger.error("Error editing comment with id: {}", commentId, e);
            throw e;
        }
    }

}
