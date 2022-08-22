package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Likes")
public class Like {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;


    @ManyToOne
    @JoinColumn(name="ArticlesLikeId")
    private Articles articles;

//    @ManyToOne
//    @JoinColumn(name="UserId")
//    private User user;

    private Boolean isArticlesLike;
    @Column
    private String username;

    public Like(Articles articles, String username, Boolean isArticlesLike) {
        this.username = username;
        this.articles = articles;
        this.isArticlesLike = isArticlesLike;
    }


//    public Like(Articles articles,User user){
//
//        this.articles=articles;
//        this.user=user;
//    }


}
