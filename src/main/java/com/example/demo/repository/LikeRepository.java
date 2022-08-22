package com.example.demo.repository;

import com.amazonaws.services.kms.model.ListKeyPoliciesRequest;
import com.example.demo.entity.Articles;
import com.example.demo.entity.Like;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Collection;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {

//    Optional<Like> findLikeByUserAndArticles(String user, Long id);
//    void deleteLikeByUserAndArticles(User user, Articles articles);



    Long countByArticles(Articles target);

//    Collection<Object> findByArticlesAndUser(Articles articles, String username);

    Collection<Object> findByArticlesAndUsername(Articles target, String userName);

    Like findByUsernameAndArticles(String username, Articles target);


//    Collection<Object> findByArticlesAndUserName(String username, Long articlesId);
}

