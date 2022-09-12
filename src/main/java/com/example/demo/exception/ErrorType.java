package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorType {

//    UsernameOrPasswordNotFoundException (400, "아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    UsernameNotFoundException (400, "사용자가 없습니다. 회원가입해주세요.", HttpStatus.BAD_REQUEST),
    PasswordNotFoundException (400, "로그인 정보를 다시 확인해 주세요.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZEDException (401, "로그인 후 이용가능합니다.", HttpStatus.UNAUTHORIZED),
    ReLogin(445, "모든 토큰이 만료되었습니다. 다시 로그인해주세요.", HttpStatus.UNAUTHORIZED),


    NotExistArticles(400, "해당 게시물이 존재하지않습니다.", HttpStatus.BAD_REQUEST);

    @Getter
    private int code;

    @Getter
    private String description;

    @Getter
    private HttpStatus httpStatus;

    ErrorType(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
