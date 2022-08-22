package com.example.demo.dto;

import com.example.demo.entity.CommentEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponDto {

    private Long articlesId;
    private String createAt;
    private Long commentId;
    private String comment;
    private String UserName;

    public CommentResponDto(CommentEntity commentEntity, String userName, String createAt) {
        this.articlesId = commentEntity.getArticlesCommentId();
        this.createAt = createAt;
        this.commentId = commentEntity.getCommentId();
        this.comment = commentEntity.getComment();
        this.UserName = userName;
    }
}
