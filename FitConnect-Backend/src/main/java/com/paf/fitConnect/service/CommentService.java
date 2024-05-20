package com.paf.fitConnect.service;

import java.util.List;
import com.paf.fitConnect.modal.Comment;

public interface CommentService {

    List<Comment> getCommentsForPost(String postId);

    Comment addCommentToPost(String postId, String content, String commentBy, String commentById ,String commentByProfile);

    void deleteComment(String postId, String commentId);

    Comment editComment(String commentId, String content);

}
