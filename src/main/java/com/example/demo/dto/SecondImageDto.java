package com.example.demo.dto;

import com.example.demo.entity.ImagePostEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SecondImageDto {

    private List<ImageDetailResponseDto> images;
    private Long articlesId;


//    public SecondImageDto(List<ImageDetailResponseDto> images, ImagePostEntity imagePostEntity) {
//        this.images = images;
//        this.articlesId = imagePostEntity.getArticlesImageId();
//    }

}
