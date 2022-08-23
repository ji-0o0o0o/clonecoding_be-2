package com.example.demo.dto;

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

    private List<MyPageImageDto> imageList;

}
