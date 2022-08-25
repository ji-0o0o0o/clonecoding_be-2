package com.example.demo.repository;


import com.example.demo.entity.Articles;
import com.example.demo.entity.ImagePostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImagePostRepository extends JpaRepository<ImagePostEntity, Long> {



    List<ImagePostEntity> findAllByArticlesImageId(Long articlesId);


    List<ImagePostEntity> findAllByUserName(String articlesId);

    List<ImagePostEntity> deleteAllByArticlesImageId(Long articlesId);
}
