package com.example.demo.controller;

import com.example.demo.dto.CommentDto;
import com.example.demo.dto.CommentResponDto;
import com.example.demo.entity.CommentEntity;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/articles/{id}/comments")
    public CommentResponDto postComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        return commentService.postComment(id, commentDto);
    }
}
