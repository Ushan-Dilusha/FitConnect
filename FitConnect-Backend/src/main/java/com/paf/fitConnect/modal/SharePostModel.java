package com.paf.fitConnect.modal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "shared_posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharePostModel {

    @Id
    private String id;
    private User sharedBy;
    private String userId;
    private Post post;
    private String description;
    private String shared;

}
