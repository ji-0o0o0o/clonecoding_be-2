package com.example.demo.repository;


import com.example.demo.entity.ImagePostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagePostRepository extends JpaRepository<ImagePostEntity, Long> {

    ImagePostEntity findByArticles_ArticlesId(Long articlesId);

    List<ImagePostEntity> findAllByArticlesImageId(Long articlesId);
    List<ImagePostEntity> findAllByUserName(String articlesId);
}
