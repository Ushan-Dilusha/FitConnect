package com.paf.fitConnect.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.paf.fitConnect.DTO.ShareDTO;
import com.paf.fitConnect.modal.Post;
import com.paf.fitConnect.modal.SharePostModel;
import com.paf.fitConnect.modal.User;
import com.paf.fitConnect.repository.PostRepository;
import com.paf.fitConnect.repository.SharePostRepository;
import com.paf.fitConnect.repository.UserRepository;
import com.paf.fitConnect.service.SharePostService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SharePostServiceImpl implements SharePostService {

    private static final Logger logger = LoggerFactory.getLogger(SharePostServiceImpl.class);

    private final SharePostRepository sharePostRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public List<SharePostModel> getSharePosts() {
        try {
            return sharePostRepository.findAll();
        } catch (DataAccessException e) {
            logger.error("Error fetching all shared posts", e);
            throw e;
        }
    }

    @Override
    public SharePostModel createSharePost(ShareDTO shareDTO) {
        try {
            Post post = postRepository.findById(shareDTO.getPostId())
                    .orElseThrow(() -> new IllegalArgumentException("Post not found"));

            User user = userRepository.findById(shareDTO.getUserid())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            SharePostModel sharePostModel = new SharePostModel();
            sharePostModel.setSharedBy(user);
            sharePostModel.setPost(post);
            sharePostModel.setDescription(shareDTO.getDescription());
            sharePostModel.setShared("shared");
            sharePostModel.setUserId(shareDTO.getUserid());
            return sharePostRepository.save(sharePostModel);
        } catch (DataAccessException | IllegalArgumentException e) {
            logger.error("Error creating shared post", e);
            throw e;
        }
    }

    @Override
    public void deleteSharedPost(String id) {
        try {
            sharePostRepository.deleteById(id);
        } catch (DataAccessException e) {
            logger.error("Error deleting shared post with id: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<SharePostModel> getSharePostsByuser(String id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            return sharePostRepository.findByUserId(id);
        } catch (DataAccessException | IllegalArgumentException e) {
            logger.error("Error fetching shared posts by user with id: {}", id, e);
            throw e;
        }
    }
}
