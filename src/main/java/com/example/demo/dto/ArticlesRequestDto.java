package com.example.demo.dto;

import com.example.demo.entity.Articles;
import com.example.demo.entity.CommentEntity;
import com.example.demo.entity.ImagePostEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticlesRequestDto {


    private String userName;
    private Long likeCount;
    private String content;
    private String createAt;
    private Long CommentCount;
    private Long articlesId;
//    private List<ImagePostEntity> images;
    private List<String> image;
    private List<CommentResponDto> commentList;





    public ArticlesRequestDto(Articles articles,String createAt) {
        this.articlesId = articles.getArticlesId();
//        this.images = articles.getImageList();
        this.userName = articles.getUserName();
        this.likeCount = articles.getLikeCount();
        this.content = articles.getContent();
        this.createAt = createAt;
        this.CommentCount = articles.getCommentCount();
//        this.commentList = articles.getCommentList();
    }

    public ArticlesRequestDto(Articles articles,List<String> image, String createAt, List<CommentResponDto> commentList) {
        this.articlesId = articles.getArticlesId();
//        this.images = articles.getImage();
        this.userName = articles.getUserName();
        this.likeCount = articles.getLikeCount();
        this.content = articles.getContent();
        this.createAt = createAt;
        this.CommentCount = articles.getCommentCount();
        this.commentList = commentList;
        this.image = image;
    }

}
