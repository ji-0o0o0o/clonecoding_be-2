package com.example.demo.controller;

import com.example.demo.dto.ArticlesResponseDto;
import com.example.demo.service.ArticlesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/mypage")
@RequiredArgsConstructor
@RestController
public class MyPageController {

    private final ArticlesService articlesService;

    //마이페이지 상세페이지
    @GetMapping("/{articlesId}")
    public ArticlesResponseDto readMypage(@PathVariable Long articlesId){
        return articlesService.readMypage(articlesId);
    }

}
