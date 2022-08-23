package com.example.demo.dto;

import com.example.demo.entity.Articles;
import com.example.demo.entity.CommentEntity;
import com.example.demo.entity.ImagePostEntity;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticlesResponseDto {


//    private String image;
    private String userName;
    private Long likeCount;
    private String content;
    private String createAt;
    private Long CommentCount;
    private List<CommentResponDto> commentList;

    private Long articlesId;

    private List<String> image;



    public ArticlesResponseDto(Articles articles, String createAt, List<CommentResponDto> commentEntities) {
        this.userName = articles.getUserName();
        this.likeCount = articles.getLikeCount();
        this.content = articles.getContent();
        this.createAt = createAt;
        this.CommentCount = articles.getCommentCount();
//        this.commentList = commentList;
        this.commentList = commentEntities;
        this.articlesId = articles.getArticlesId();
    }

    public ArticlesResponseDto(Articles articles, String createAt, List<CommentResponDto> commentEntities, List<String> imagePostEntities) {
        this.userName = articles.getUserName();
        this.likeCount = articles.getLikeCount();
        this.content = articles.getContent();
        this.createAt = createAt;
        this.CommentCount = articles.getCommentCount();
        this.commentList = commentEntities;
        this.articlesId = articles.getArticlesId();
        this.image = imagePostEntities;
    }
}
