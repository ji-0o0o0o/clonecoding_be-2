package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @JsonIgnore
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userName", length = 50, unique = true)
    private String userName;

    @Column(name = "userId", length = 50)
    private String userId;

    @Column(name = "userEmail", length = 50)
    private String userEmail;

    @JsonIgnore
    @Column(name = "password", length = 100)
    private String password;

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private AuthorityEntity authorityEntity;


//    public User(String username, String password, String nickname, boolean activated, AuthorityEntity authorityEntity) {
//        this.username = username;
//        this.password = password;
//        this.nickname = nickname;
//        this.activated = activated;
//        this.authorityEntity = authorityEntity;
//    }
}