package com.example.demo.entity;

import com.example.demo.dto.CommentDto;
import com.example.demo.util.TimeStamped;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "comment")
@Entity
public class CommentEntity extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "articles_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Articles articles;

    @Column
    private String userName;

    private Long articlesCommentId;

    public CommentEntity(CommentDto commentDto) {
        this.comment = commentDto.getComment();
    }

    public CommentEntity(Articles articles, CommentDto commentDto, String userName) {
        this.articles = articles;
        this.comment = commentDto.getComment();
        this.userName = userName;
        this.articlesCommentId = articles.getArticlesId();

    }

}

