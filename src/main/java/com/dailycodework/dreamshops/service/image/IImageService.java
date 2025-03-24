package com.dailycodework.dreamshops.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.model.Image;

public interface IImageService {
    
    Image getImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    Image updateImage(MultipartFile file, Long imageId);
    void deleteImageById(Long id);

}
