package com.paf.fitConnect.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paf.fitConnect.DTO.ShareDTO;
import com.paf.fitConnect.modal.SharePostModel;
import com.paf.fitConnect.service.SharePostService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/share")
public class SharePostController {

    private SharePostService sharePostService;

    @GetMapping
    public List<SharePostModel> getSharePosts() {
        return sharePostService.getSharePosts();
    }

    @PostMapping
    public ResponseEntity<SharePostModel> createSharePost(@RequestBody ShareDTO shareDTO) {
        SharePostModel savedPost = sharePostService.createSharePost(shareDTO);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        sharePostService.deleteSharedPost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public List<SharePostModel> getSharePostsByUserId(@PathVariable String id) {
        return sharePostService.getSharePostsByuser(id);
    }

}
