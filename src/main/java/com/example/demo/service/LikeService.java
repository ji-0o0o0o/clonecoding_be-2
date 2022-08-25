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

//    public String getUserName() { //로그인한 유저에 대한 유저명 받아오기 -> 좋아요시 유저네임을 명시해서 넘겨줌
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName(); //로그인해서 뽑은 아이디르 넣음
//        Optional<User> users = userRepository.findById(Long.valueOf(userName));
//
//        return users.get().getUserName();
//    }

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

//        Optional<User> usernames = userRepository.findById(Long.valueOf(username));
//        System.out.println(usernames);
//
//        log.info("{}", usernames);
//
//        //로그인한 유저를 찾았지만,명시적으로 user에
//        String user = getUser();
//
//        //게시물 존재 여부를 확인한 후 좋아요를 했는지를 확인하기 위함
//        Articles articles = articlesRepository.findById(articlesId).orElseThrow(() -> new NullPointerException("게시물이 존재하지 않습니다."));



//        //로그인한 유저,해당 게시글 pk값으로 좋아요 여부 확인하기
//        Optional<Like> click = likeRepository.findLikeByUserAndArticles(user, articlesId);
//        //조건에 해당하는 like를 찾아온다
//        if(click.isPresent()){  //Optional 객체가 값을 가지고 있다면 true, 값이 없다면 false 리턴
//            //즉 click에 값이 있으면 이 조건문이 시행되어 좋아요가 취소되고 그것이 아니라면 else로 이동해 좋아요를 활성화
//            //클릭을 통해 해당 게시글의 정보와 클릭한 유저 정보를 받아와서 좋아요가 있으므로 좋아요를 지운다
//
//            articlesRepository.downlikeCount(articles.getArticlesId());
//            likeRepository.deleteLikeByUserAndArticles(click.get().getUser(),articles);
//
//
//            return new LikeDto(user);
//
//
//        }else{
//            //좋아유
//            Like like = new Like();
//            likeRepository.save(like);
//            articlesRepository.uplikeCount(articles.getArticlesId());
//
//
//            return new LikeDto(user);
//        }

    }
}
