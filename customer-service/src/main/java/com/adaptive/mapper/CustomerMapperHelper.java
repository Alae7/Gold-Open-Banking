package com.adaptive.mapper;


import com.adaptive.entity.Image;
import com.adaptive.repository.ImageRepository;
import com.adaptive.utils.CloudinaryService;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
public class CustomerMapperHelper {


    @Autowired
    private   CloudinaryService cloudinaryService;

    @Autowired
    private   ImageRepository imageRepository;

    @Named("createImage")
    public Image createImage(MultipartFile  file) {

        Image image = new Image();
        try {
            Map<String, Object> uploadResult = cloudinaryService.uploadImage(file);
            image.setUrl((String) uploadResult.get("url"));
            image.setPublicId((String) uploadResult.get("public_id"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageRepository.save(image);
    }


}
