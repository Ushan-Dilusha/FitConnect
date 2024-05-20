package com.paf.fitConnect.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private String id;
    private String title;
    private String date;
    private List<String> images;
    private String video;
    private String description;

}
