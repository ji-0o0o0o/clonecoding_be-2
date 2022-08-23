package com.example.demo.controller;

import com.example.demo.dto.MyPageDto;
import com.example.demo.entity.Articles;
import com.example.demo.service.ArticlesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyPageController {

    private ArticlesService articlesService;

    public MyPageController(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @GetMapping("/api/mypage")
    public MyPageDto getMyPage() {
        return articlesService.getMypage();
    }
}
