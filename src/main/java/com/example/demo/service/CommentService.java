package com.example.demo.service;

import com.example.demo.dto.CommentDto;
import com.example.demo.dto.CommentResponDto;
import com.example.demo.entity.Articles;
import com.example.demo.entity.CommentEntity;
import com.example.demo.exception.ErrorType;
import com.example.demo.exception.EveryExceptions.NullPointerException;
import com.example.demo.repository.ArticlesRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Service
public class CommentService {

    private CommentRepository commentRepository;

    private ArticlesRepository articlesRepository;
    private UserService userService;
    private final Time time;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          ArticlesRepository articlesRepository, UserService userService,
                          Time time) {
        this.commentRepository = commentRepository;
        this.articlesRepository = articlesRepository;
        this.userService = userService;
        this.time = time;
    }




    @Transactional
    public CommentResponDto postComment(Long id, CommentDto commentDto) {

        String username = userService.getSigningUserId();
        Articles articles = articlesRepository.findById(id)
                .orElseThrow(()-> new NullPointerException(ErrorType.NotExistArticles));

        CommentEntity comment = new CommentEntity(articles, commentDto, username);
        articles.addComment(comment);
        articles.setCommentCount(articles.getCommentList().size());

        commentRepository.save(comment);

        long rightNow = ChronoUnit.MINUTES.between(comment.getCreatedAt(), LocalDateTime.now());

        CommentResponDto commentResponDto = new CommentResponDto(comment, time.times(rightNow));


        return commentResponDto;
    }

}
