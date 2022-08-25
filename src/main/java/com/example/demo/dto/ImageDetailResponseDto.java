package com.example.demo.dto;

import com.example.demo.entity.ImagePostEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageDetailResponseDto {

    private Long imageId;
    private String Image;

//    public ImageDetailResponseDto(ImagePostEntity imagePostEntity) {
//        this.imageId = imagePostEntity.getImageId();
//        this.Image = imagePostEntity.getImage();
//    }
}
