package com.atabekyan.image.service.services;

import com.atabekyan.image.service.dao.ImageDao;
import com.atabekyan.image.service.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageDao imageDao;

    @Autowired
    public ImageService(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    public void updateImageTitle(Long image_id, String title) {
        Optional<Image> imageFromDB = imageDao.findById(image_id);

        System.out.println("title " + title);
        System.out.println("image id " + image_id);
        if(imageFromDB.isPresent()) {
            Image image = imageFromDB.get();
            image.setTitle(title);
            imageDao.save(image);
        }
    }
}
