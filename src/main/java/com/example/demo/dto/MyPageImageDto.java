package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.annotation.Secured;

@Builder
@Getter
@Setter
public class MyPageImageDto {

    private String image;
    private Long articlesId;

}
