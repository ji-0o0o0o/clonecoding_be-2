package com.example.demo.service;


import java.util.List;
import java.util.Optional;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.RegisterDto;
import com.example.demo.entity.AuthorityEntity;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.SecurityUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//UserRepository, PasswordEncoder를 주입받는 UserService클래스를 생성
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String getSigningUserId(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
//        return Long.valueOf(userId);

    }

    @Transactional
    //signup 메소드는 이미 같은 username으로 가입된 유저가 있는지 확인하고
    //UserDto객체의 정보들을 기반으로 권한 객체와 유저 객체를 생성하여 DB저장

    public User signup(RegisterDto registerDto) {
        if (userRepository.findOneWithAuthorityEntityByUserName(registerDto.getUserName()).orElse(null) != null) {
            throw new RuntimeException("이미 사용중인 아이디입니다.");
        }
        if (userRepository.findOneWithAuthorityEntityByUserEmail(registerDto.getUserEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 사용중인 이메일입니다");
        }

        //해당 메소드롤 생성된 유저는 ROLE_USER권한을 소유해서
        //ROLE_ADMIN 권한만 호출할 수 있는 API는 호출불가능함
        AuthorityEntity authorityEntity = AuthorityEntity.ROLE_USER;
//                .authorityName("ROLE_USER")
//                .build();

        User user = User.builder()
                .userName(registerDto.getUserName())
                .userEmail(registerDto.getUserEmail())
                .userId(registerDto.getUserId())
                .activated(true)
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .authorityEntity(AuthorityEntity.ROLE_USER)
                .build();

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    //getUserWithAuthorities : username을 파라미터롤 받아 해당유저 정보 및 권한정보 리턴
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthorityEntityByUserName(username);
    }

    @Transactional(readOnly = true)
    //getMyUserWithAuthorities : SecurityUtil의 getCurrentUsername()
    //메소드가 리턴하는 username의 유저 및 권한 정보를 리턴함
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthorityEntityByUserName);
    }

    @Transactional
    public Boolean validateId(LoginDto loginDto) {
        List<User> getId = userRepository.findAll();
        System.out.println(loginDto.getUserId());
        for (User targets : getId) {
            if (loginDto.getUserId().equals(targets.getUserId())) {
                return false;
            }
        }
        return true;
    }

    @Transactional
    public Boolean validateEmail(LoginDto loginDto) {
        List<User> target = userRepository.findAll();
        System.out.println(loginDto.getUserEmail());
        for (User targets : target) {
            if (loginDto.getUserEmail().equals(targets.getUserEmail())) {
                return false;
            }
        }
        return true;
    }

}