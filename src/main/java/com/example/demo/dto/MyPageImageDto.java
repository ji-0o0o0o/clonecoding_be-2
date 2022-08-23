package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

@Builder
@Getter
@Setter
public class MyPageImageDto {

    private List<String> image;
    private Long articlesId;

}
