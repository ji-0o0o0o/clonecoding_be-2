package com.example.demo.service;

import com.example.demo.dto.LikeDto;
import com.example.demo.entity.Articles;
import com.example.demo.entity.Like;
import com.example.demo.entity.User;
import com.example.demo.repository.ArticlesRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Optional;



@Service
@Slf4j
public class LikeService {

    private final LikeRepository likeRepository;
    private final ArticlesRepository articlesRepository;

    private final UserRepository userRepository;



    public String getUser() {  //로그인한 유저에 대한 정보 받아오기
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("유저를 찾지 못했습니다."));
        return userId;
    }


    @Autowired
    public LikeService(LikeRepository likeRepository, ArticlesRepository articlesRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.articlesRepository = articlesRepository;
        this.userRepository = userRepository;

    }

    public LikeDto likeArticles(Long articlesId) {

        Articles target = articlesRepository.findById(articlesId).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지않습니다")
        );

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Boolean isArticlesLike = false;
        Like like = new Like(target, username, isArticlesLike);


        long likecount = articlesRepository.findById(articlesId).get().getLikeCount();

        if (likeRepository.findByArticlesAndUsername(target, username).isEmpty()) {
            articlesRepository.uplikeCount(target.getArticlesId());
            likeRepository.save(like);

            LikeDto likeDto = new LikeDto(articlesId, likecount+1, true);
            return likeDto;
        } else {
            articlesRepository.downlikeCount(target.getArticlesId());
            likeRepository.delete(likeRepository.findByUsernameAndArticles(username, target));

        }
            LikeDto likeDto = new LikeDto(articlesId, likecount-1, false);
        return likeDto;


    }
}
