package com.paf.fitConnect.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private String content;
    private String commentBy;
    private String commentById;
    private String commentByProfile;

}
