package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Articles;
import com.example.demo.entity.CommentEntity;
import com.example.demo.entity.ImagePostEntity;
import com.example.demo.exception.ErrorType;
import com.example.demo.exception.EveryExceptions.NullPointerException;
import com.example.demo.repository.ArticlesRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ImagePostRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.service.s3.S3Uploader;
import com.example.demo.util.Time;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
    private final ImagePostRepository imagePostRespository;

    public ArticlesService(ArticlesRepository articlesRepository,
                           CommentRepository commentRepository, UserService userService,
                           S3Uploader s3Uploader, Time time, LikeRepository likeRepository,
                           ImagePostRepository imagePostRepository) {
        this.articlesRepository = articlesRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.s3Uploader = s3Uploader;
        this.time = time;
        this.likeRepository = likeRepository;
        this.imagePostRespository = imagePostRepository;
    }


    // 메안페이지 생성
    public ArticlesRequestDto postArticles(List<MultipartFile> multipartFile, ArticlesDto articlesDto) throws IOException {
        if (multipartFile != null) {

            String username = userService.getSigningUserId();

            Articles articles = new Articles(articlesDto, username);
            articlesRepository.save(articles);

            List<ImagePostEntity> imgbox = new ArrayList<>();
            //          이미지 업로드
            for (MultipartFile uploadedFile : multipartFile) {

                ImagePostEntity imagePostEntity = ImagePostEntity.builder()
                        .image(s3Uploader.upload(uploadedFile))
                        .userName(userService.getSigningUserId())
                        .articlesImageId(articles.getArticlesId())
                        .build();
                imgbox.add(imagePostEntity);

                imagePostRespository.save(imagePostEntity);
            }
            long rightNow = ChronoUnit.MINUTES.between(articles.getCreatedAt(), LocalDateTime.now());
            ArticlesRequestDto articlesRequestDto = new ArticlesRequestDto(articles, time.times(rightNow));
            return articlesRequestDto;


        } else {
            Articles articles = new Articles(articlesDto, userService.getSigningUserId());
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


        for (Articles findArticle : articlesList) {

            List<String> data = new ArrayList<>();

            List<CommentEntity> commentList = commentRepository.findByArticles_ArticlesId(findArticle.getArticlesId());
            List<CommentResponDto> commentBox = new ArrayList<>();
//
            for (CommentEntity datas : commentList) {
                if (datas.getArticles().getArticlesId().equals(findArticle.getArticlesId())) {
                    log.info("{}", datas);
                    long commentRightNow = ChronoUnit.MINUTES.between(datas.getCreatedAt(), LocalDateTime.now());
                    CommentResponDto commentResponDto = new CommentResponDto(datas, time.times(commentRightNow));

                    commentBox.add(commentResponDto);
                }
            }

            List<ImagePostEntity> target = imagePostRespository.findAllByArticlesImageId(findArticle.getArticlesId());
            for (ImagePostEntity imagePostEntity : target) {
                data.add(imagePostEntity.getImage());
            }

            long commentRightNow = ChronoUnit.MINUTES.between(findArticle.getCreatedAt(), LocalDateTime.now());
            articlesRequestDtoList.add(new ArticlesRequestDto(findArticle, data, time.times(commentRightNow), commentBox));
        }


        return articlesRequestDtoList;
    }

    //메인 상세 페이지 조회
    public ArticlesRequestDto readArticles(Long articlesId) {

        Articles articles = articlesRepository.findById(articlesId)
                .orElseThrow(() -> new NullPointerException(ErrorType.NotExistArticles));


        long articlesRightNow = ChronoUnit.MINUTES.between(articles.getCreatedAt(), LocalDateTime.now());

        List<CommentEntity> commentList = commentRepository.findByArticles_ArticlesId(articlesId);
        List<CommentResponDto> commentBox = new ArrayList<>();
//
        for (CommentEntity datas : commentList) {
            if (datas.getArticles().getArticlesId().equals(articlesId)) {
                log.info("{}", datas);
                long commentRightNow = ChronoUnit.MINUTES.between(datas.getCreatedAt(), LocalDateTime.now());
                CommentResponDto commentResponDto = new CommentResponDto(datas, time.times(commentRightNow));

                commentBox.add(commentResponDto);
            }
        }


        List<String> data = new ArrayList<>();

        List<ImagePostEntity> target = imagePostRespository.findAllByArticlesImageId(articlesId);
        for (ImagePostEntity imagePostEntity : target) {
            data.add(imagePostEntity.getImage());
        }
        ArticlesRequestDto articlesResponseDto = new ArticlesRequestDto(articles, data, time.times(articlesRightNow), commentBox);
        return articlesResponseDto;
    }

    //메인 페이지 수정
    @Transactional
    public ArticlesRequestDto updateArticles(Long articlesId, ArticlesDto articlesDto) {
        Articles articles = articlesRepository.findById(articlesId)
                .orElseThrow(() -> new NullPointerException(ErrorType.NotExistArticles));
        String user = userService.getSigningUserId();

        ArticlesRequestDto articlesRequestDto = new ArticlesRequestDto(articles, null);
        if (user.equals(articles.getUserName())) {
            articles.updateArticles(articlesDto);
            return articlesRequestDto;
        }
        return null;
    }
    //메인 페이지 삭제

    public ArticleDeleteDto deleteArticles(Long articlesId) {
        Articles articles = articlesRepository.findById(articlesId)
                .orElseThrow(() -> new NullPointerException(ErrorType.NotExistArticles));
        String user = userService.getSigningUserId();

        ArticleDeleteDto articleDeleteDto = new ArticleDeleteDto(articlesId, "삭제가 실패하였습니다.");
        ArticleDeleteDto articleDeleteDto1 = new ArticleDeleteDto(articlesId, "삭제가 되었습니다.");

        if (user.equals(articles.getUserName())) {
            articlesRepository.delete(articles);
            return articleDeleteDto1;
        } else return articleDeleteDto;
    }


    //마이페이지 상세페이지
    public ArticlesRequestDto readMypage(Long articlesId) {

        Articles articles = articlesRepository.findById(articlesId)
                .orElseThrow(() -> new NullPointerException(ErrorType.NotExistArticles));
//        작성시간


        long articlesRightNow = ChronoUnit.MINUTES.between(articles.getCreatedAt(), LocalDateTime.now());
        List<CommentEntity> commentList = commentRepository.findByArticles_ArticlesId(articlesId);
        List<CommentResponDto> commentBox = new ArrayList<>();
//
        for (CommentEntity datas : commentList) {
            if (datas.getArticles().getArticlesId().equals(articlesId)) {
                log.info("{}", datas);
                long commentRightNow = ChronoUnit.MINUTES.between(datas.getCreatedAt(), LocalDateTime.now());
                CommentResponDto commentResponDto = new CommentResponDto(datas, time.times(commentRightNow));

                commentBox.add(commentResponDto);
            }
        }
//        이미지 첨부
        List<String> data = new ArrayList<>();

        List<ImagePostEntity> target = imagePostRespository.findAllByArticlesImageId(articlesId);
        for (ImagePostEntity imagePostEntity : target) {
            data.add(imagePostEntity.getImage());
        }

        ArticlesRequestDto articlesResponseDto = new ArticlesRequestDto(articles, data, time.times(articlesRightNow), commentBox);
        return articlesResponseDto;
    }


    public MyPageDto getMypage() {
        List<ImagePostEntity> target = imagePostRespository.findAllByUserName(userService.getSigningUserId());

        List<SecondImageDto> imageBox = new ArrayList<>();

//      이미지 첨부

        for (ImagePostEntity imagePostEntity : target) {


            //          중간
            List<ImageDetailResponseDto> imageDetailBox = new ArrayList<>();

            SecondImageDto secondImageDto = SecondImageDto.builder()
                    .articlesId(imagePostEntity.getArticlesImageId())
                    .images(imageDetailBox)
                    .build();
            imageBox.add(secondImageDto);

//          최종
            ImageDetailResponseDto imageDetailResponseDto = ImageDetailResponseDto.builder()
                    .Image(imagePostEntity.getImage())
                    .imageId(imagePostEntity.getImageId())
                    .build();
            imageDetailBox.add(imageDetailResponseDto);


        }

        for (int i = 0; i < imageBox.size(); i++) {
            for (int j = 1; j < imageBox.size(); j++) {
                if (imageBox.get(i).getArticlesId().equals(imageBox.get(j).getArticlesId())) {
                    imageBox.remove(j);
                }
            }
        }


        Collections.reverse(imageBox);

        MyPageDto myPageDto = MyPageDto.builder()
                .articlesCount(imageBox.size())
                .userName(userService.getSigningUserId())
                .imageList(imageBox)
                .build();

        return myPageDto;

    }


//      마이페이지 메인
//    public MyPageDto getMypage() {
//        List<Articles> datas = articlesRepository.findAllByUserName(userService.getSigningUserId());
//        log.info("{}", datas);
//        List<SecondImageDto> imageBox  = new ArrayList<>();
//        List<ImageDetailResponseDto> imageDetailBox  = new ArrayList<>();
//
//

//      이미지 첨부
//        List<ImagePostEntity> target = imagePostRespository.findAllByUserName(userService.getSigningUserId());
//        for (ImagePostEntity imagePostEntity : target) {
//
//            ImageDetailResponseDto imageDetailResponseDto = ImageDetailResponseDto.builder()
//                    .Image(imagePostEntity.getImage())
//                    .imageId(imagePostEntity.getImageId())
//                    .build();
//            imageDetailBox.add(imageDetailResponseDto);
//
//                SecondImageDto secondImageDto = SecondImageDto.builder()
//                        .articlesId(imagePostEntity.getArticlesImageId())
//                        .images(imageDetailBox)
//                        .build();
//                imageBox.add(secondImageDto);
//
//        }
//
//        Collections.reverse(imageBox);
//
//
//        MyPageDto myPageDto = MyPageDto.builder()
//                .articlesCount(target.size())
//                .userName(userService.getSigningUserId())
//                .imageList(imageBox)
//                .build();
//
//        return myPageDto;
//
//    }
}

