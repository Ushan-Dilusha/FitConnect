package com.paf.fitConnect.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.paf.fitConnect.modal.SharePostModel;

@Repository
public interface SharePostRepository extends MongoRepository<SharePostModel, String> {
    List<SharePostModel> findByUserId(String userId);
}
