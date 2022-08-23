package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Articles;
import com.example.demo.entity.CommentEntity;
import com.example.demo.entity.ImagePost;
import com.example.demo.repository.ArticlesRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ImagePostRespository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.service.s3.S3Uploader;
import com.example.demo.util.Time;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.awt.image.ImageRepresentation;

import javax.imageio.ImageReader;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class ArticlesService {

    private final ArticlesRepository articlesRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final S3Uploader s3Uploader;
    private final Time time;
    private final LikeRepository likeRepository;
    private final ImagePostRespository imagePostRespository;

    public ArticlesService(ArticlesRepository articlesRepository,
                           CommentRepository commentRepository, UserService userService,
                           S3Uploader s3Uploader, Time time, LikeRepository likeRepository,
                           ImagePostRespository imagePostRespository) {
        this.articlesRepository = articlesRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.s3Uploader = s3Uploader;
        this.time = time;
        this.likeRepository = likeRepository;
        this.imagePostRespository = imagePostRespository;
    }

    private Long getTime() {
        Articles articles = new Articles();
        return ChronoUnit.MINUTES.between(articles.getCreatedAt(), LocalDateTime.now());

    }

    // 메안페이지 생성
    public ArticlesRequestDto postArticles(List<MultipartFile> multipartFile, ArticlesDto articlesDto) throws IOException {

        List<ImagePost> imageList = new ArrayList<>();
        if (multipartFile != null) {
            //          이미지 업로드
//            imageList.add(s3Uploader.upload(multipartFile));

            for (MultipartFile uploadedFile : multipartFile) {
                s3Uploader.upload(uploadedFile);

                ImagePost imageposts = ImagePost.builder()
                        .image(s3Uploader.upload(uploadedFile))
                        .build();

                imagePostRespository.save(imageposts);
            }

//            String image = s3Uploader.upload(multipartFile);
            String username = userService.getSigningUserId();

            Articles articles = new Articles(articlesDto, username);
            articlesRepository.save(articles);
//            작성시간 조회

            long rightNow = ChronoUnit.MINUTES.between(articles.getCreatedAt(), LocalDateTime.now());
            ArticlesRequestDto articlesRequestDto = new ArticlesRequestDto(articles, time.times(rightNow));
            return articlesRequestDto;

        } else {
            Articles articles = new Articles(articlesDto , userService.getSigningUserId());
            articlesRepository.save(articles);
//            작성시간 조회

            long rightNow = ChronoUnit.MINUTES.between(articles.getCreatedAt(), LocalDateTime.now());
            ArticlesRequestDto articlesRequestDto = new ArticlesRequestDto(articles, time.times(rightNow));
            return articlesRequestDto;
        }
    }


    // 메인 페이지 작성글 목록 조회
    public List<ArticlesRequestDto> readAllArticles() {
        List<Articles> articlesList = articlesRepository.findAllByOrderByCreatedAtDesc();
        List<ArticlesRequestDto> articlesRequestDtoList = new ArrayList<>();

        for (Articles articles:articlesList) {

        //  작성시간 조회
            long rightNow = ChronoUnit.MINUTES.between(articles.getCreatedAt(), LocalDateTime.now());
            articlesRequestDtoList.add(new ArticlesRequestDto(articles, time.times(rightNow)));
        }
        return articlesRequestDtoList;
    }

    //메인 상세 페이지 조회
    public ArticlesResponseDto readArticles(Long articlesId) {

        Articles articles = articlesRepository.findById(articlesId)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재하지않습니다."));

        List<CommentEntity> commentList = commentRepository.findByArticles_ArticlesId(articlesId);
        List<CommentResponDto> commentBox = new ArrayList<>();

        long articlesRightNow = ChronoUnit.MINUTES.between(articles.getCreatedAt(), LocalDateTime.now());

        for (CommentEntity datas : commentList) {
            if (datas.getArticles().getArticlesId().equals(articlesId)) {
                log.info("{}",datas);
                long commentRightNow = ChronoUnit.MINUTES.between(datas.getCreatedAt(), LocalDateTime.now());
                CommentResponDto commentResponDto = new CommentResponDto(datas, userService.getSigningUserId(), time.times(commentRightNow));

                commentBox.add(commentResponDto);
            }

        }

        ArticlesResponseDto articlesResponseDto = new ArticlesResponseDto(articles, time.times(articlesRightNow), commentBox);
        return articlesResponseDto;
    }

    //메인 페이지 수정
    @Transactional
    public ArticlesRequestDto updateArticles(Long articlesId, ArticlesDto articlesDto) {
        Articles articles = articlesRepository.findById(articlesId)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재하지않습니다."));
        String user = userService.getSigningUserId();

        ArticlesRequestDto articlesRequestDto = new ArticlesRequestDto(articles, null);
        if(user.equals(articles.getUserName())){
            articles.updateArticles(articlesDto);
//            return ResponseEntity.status(HttpStatus.OK).body(articlesDto).toString()+"";//저장한 정보 출력해준다
            return articlesRequestDto;
        }return null;
    }
    //메인 페이지 삭제

    public String deleteArticles(Long articlesId) {
        Articles articles = articlesRepository.findById(articlesId)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재하지않습니다."));
        String user = userService.getSigningUserId();

        if(user.equals(articles.getUserName())){
            articlesRepository.delete(articles);
            return "삭제가 되었습니다.";
        }else return "삭제가 실패하였습니다.";
    }


    //마이페이지 상세페이지
    public ArticlesResponseDto readMypage(Long articlesId) {

        Articles articles = articlesRepository.findById(articlesId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지않습니다."));
//        작성시간

        List<CommentEntity> commentList = commentRepository.findByArticles_ArticlesId(articlesId);
        List<CommentResponDto> commentBox = new ArrayList<>();

        long articlesRightNow = ChronoUnit.MINUTES.between(articles.getCreatedAt(), LocalDateTime.now());

        for (CommentEntity datas : commentList) {
            if (datas.getArticles().getArticlesId().equals(articlesId)) {
                log.info("{}",datas);
                long commentRightNow = ChronoUnit.MINUTES.between(datas.getCreatedAt(), LocalDateTime.now());
                CommentResponDto commentResponDto = new CommentResponDto(datas, userService.getSigningUserId(), time.times(commentRightNow));

                commentBox.add(commentResponDto);
            }

        }
        ArticlesResponseDto articlesResponseDto = new ArticlesResponseDto(articles, time.times(articlesRightNow), commentBox);
        return articlesResponseDto;
    }


    public MyPageDto getMypage() {
        List<Articles> datas = articlesRepository.findAllByUserName(userService.getSigningUserId());
        log.info("{}", datas);

        List<MyPageImageDto> box = new ArrayList<>();
        for (Articles data : datas) {
            System.out.println(data.getImage());
            MyPageImageDto myPageImageDto = MyPageImageDto.builder()
                    .articlesId(data.getArticlesId())
                    .image(data.getImage())
                    .build();
            box.add(myPageImageDto);
        }



        MyPageDto myPageDto = MyPageDto.builder()
                .articlesCount(datas.size())
                .userName(userService.getSigningUserId())
                .imageList(box)
                .build();

        return myPageDto;

    }
}

