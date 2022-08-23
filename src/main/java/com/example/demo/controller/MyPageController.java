package com.example.demo.controller;


import com.example.demo.dto.ArticlesResponseDto;
import com.example.demo.service.ArticlesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.MyPageDto;
import com.example.demo.entity.Articles;
import com.example.demo.service.ArticlesService;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/mypage")
@RestController
public class MyPageController {

    private ArticlesService articlesService;

    public MyPageController(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }
    //마이페이지 상세페이지

    @GetMapping("/{articlesId}")
    public ArticlesResponseDto readMypage(@PathVariable Long articlesId){
        return articlesService.readMypage(articlesId);
    }



    @GetMapping("")
    public MyPageDto getMyPage() {
        return articlesService.getMypage();
    }
}
