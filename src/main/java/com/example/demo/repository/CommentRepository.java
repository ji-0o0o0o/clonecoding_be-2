package com.example.demo.repository;

import com.example.demo.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query("select p from CommentEntity p ORDER BY p.createdAt DESC")
    List<CommentEntity> findByArticles_ArticlesId(Long articlesId);
}
