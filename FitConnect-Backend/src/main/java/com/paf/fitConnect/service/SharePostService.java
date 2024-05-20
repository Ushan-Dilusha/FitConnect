package com.paf.fitConnect.service;

import java.util.List;
import com.paf.fitConnect.DTO.ShareDTO;
import com.paf.fitConnect.modal.SharePostModel;

public interface SharePostService {

 List<SharePostModel> getSharePosts();

    SharePostModel createSharePost(ShareDTO shareDTO);
    void deleteSharedPost(String id);

    List<SharePostModel> getSharePostsByuser(String id);

}
