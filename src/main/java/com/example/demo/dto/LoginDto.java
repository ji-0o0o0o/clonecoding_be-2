package com.example.demo.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @Size(max = 50)
    private String userId;

    @Size(max = 50)
    private String userEmail;

    @Size(max = 50)
    private String userName;

    @NotNull
    @Size(max = 100)
    private String password;
}