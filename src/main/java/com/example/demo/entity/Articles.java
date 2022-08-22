package com.example.demo.entity;

import com.example.demo.dto.ArticlesDto;
import com.example.demo.util.TimeStamped;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Articles extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articlesId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)// length = ?
    private String content;


    @Column
    private String image;


    @Column
    private Long likeCount=0L;

    @Column
    private long commentCount=0L;

//    @JoinColumn
//    @JsonBackReference
//    @JsonIgnore
//    private Boolean isArticlesLike = false;


    public Articles(ArticlesDto articlesDto, String image, String userName) {
        this.content = articlesDto.getContent();
        this.image = image;
        this.userName = userName;
    }

    public Articles(ArticlesDto articlesDto,String userName) {
        this.content = articlesDto.getContent();
        this.userName = userName;
    }

    public void updateArticles(ArticlesDto articlesDto) {
        this.content = articlesDto.getContent();
    }

//    public void setImage(String url) {
//        this.image = url;
//    }

    @OneToMany(mappedBy = "articles",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CommentEntity> commentList = new ArrayList<>();

    public void addComment(CommentEntity comment) {
        this.commentList.add(comment);
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

//    @OneToMany(mappedBy = "article") //생성 삭제가 많이 일어나니까 mappedBy
//    @JsonIgnore
//    private List<Like> likeList = new ArrayList<>();

}
