package com.example.demo.dto;

import com.example.demo.entity.Articles;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ArticlesRequestDto {


    private String image;
    private String userName;
    private Long likeCount;
    private String content;
    private String createAt;
    private Long CommentCount;
    private Long articlesId;

    public ArticlesRequestDto(Articles articles, String createAt) {
        this.articlesId = articles.getArticlesId();
        this.image = articles.getImage();
        this.userName = articles.getUserName();
        this.likeCount = articles.getLikeCount();
        this.content = articles.getContent();
        this.createAt = createAt;
        this.CommentCount = articles.getCommentCount();
    }
}
