package com.paf.fitConnect.modal;

import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    private String id;
    private String userId;
    private String username;
    private String userProfile;
    private String title;
    private String description;
    private List<String>  images;
    private String video;
    @CreatedDate
    private String date;
    private int likeCount;
    private List<String> likedBy;
    @DBRef
    private List<Comment> comments;
    private List<String> sharedBy;

    public List<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }

    public void addLikedBy(String userId) {
        likedBy.add(userId);
    }

    public void removeLikedBy(String userId) {
        likedBy.remove(userId);
    }
}
