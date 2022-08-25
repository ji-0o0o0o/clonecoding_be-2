package com.example.demo.dto;

import com.example.demo.entity.ImagePostEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class MyPageDto {

    private String userName;

    private int articlesCount;

    private List<SecondImageDto> imageList;

//    public MyPageDto(ImagePostEntity imagePostEntity, int articlesCount, List<SecondImageDto> imageList) {
//        this.userName = imagePostEntity.getUserName();
//        this.articlesCount = articlesCount;
//        this.imageList = imageList;
//    }
}
