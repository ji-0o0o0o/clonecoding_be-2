package com.example.demo.repository;

import com.example.demo.entity.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ArticlesRepository extends JpaRepository<Articles, Long> {
    List<Articles> findAllByOrderByCreatedAtDesc();


    @Transactional
    @Modifying
    @Query("update Articles m set m.likeCount = m.likeCount+1 where m.articlesId = :id")
    int uplikeCount(Long id);
    //해당 아이디의 카운터를 1 업,

    @Transactional
    @Modifying
    @Query("update Articles m set m.likeCount = m.likeCount-1 where m.articlesId = :id")
    int downlikeCount(Long id);
    //해당 아이디의 카운터를 1 다운.,

}
