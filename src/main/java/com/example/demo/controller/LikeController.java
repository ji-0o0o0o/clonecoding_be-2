package com.example.demo.controller;


import com.example.demo.dto.LikeDto;
import com.example.demo.service.LikeService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController

public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/articles/{articlesId}/like")
    public LikeDto clickLike(@PathVariable Long articlesId) {
        return likeService.likeArticles(articlesId);

    }
}