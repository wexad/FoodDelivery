package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.handler.exceptions.ProductNotFoundException;
import com.wexad.BurgerHub.model.Image;
import com.wexad.BurgerHub.repository.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void save(Image image) {
        imageRepository.save(image);
    }
}
