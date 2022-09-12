package com.example.demo.dto;


import lombok.*;

@Getter
@Setter
public class LikeDto {

    public LikeDto(Long articlesId, Long likeCount, boolean isArticlesLike) {
        this.articlesId = articlesId;
        this.likeCount = likeCount;
        this.isArticlesLike = isArticlesLike;
    }

    //    private Boolean isArticlesLike;
    private Long articlesId;
    private Long likeCount;
    private boolean isArticlesLike;
}
