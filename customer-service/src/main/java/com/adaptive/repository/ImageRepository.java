package com.adaptive.repository;

import com.adaptive.entity.Image;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ImageRepository extends MongoRepository<Image, String> {


    Image findImageByUrl(String url);

}
