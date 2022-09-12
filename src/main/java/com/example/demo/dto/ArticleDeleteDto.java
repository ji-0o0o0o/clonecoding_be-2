package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDeleteDto {

    private Long articlesId;

    private String msg;

    public ArticleDeleteDto(Long articlesId, String msg) {
        this.articlesId = articlesId;
        this.msg = msg;
    }
}
