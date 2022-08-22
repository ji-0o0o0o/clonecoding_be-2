//package com.example.demo.testrunner;
//
//import com.example.demo.entity.User;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import static com.example.demo.entity.AuthorityEntity.ROLE_USER;
//
//
//@RequiredArgsConstructor
//@Component
//public class TestDataRunner implements ApplicationRunner {
//
//    UserService userService;
//
//
//    UserRepository userRepository;
//
//    PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public TestDataRunner(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//// 테스트 User 생성
//        User testUser = new User("1234", passwordEncoder.encode("1234"),"1234",true, null);
//        User testUser1 = new User("2345", passwordEncoder.encode("2345"),"2345",true, null);
//        User testUser2 = new User("3456", passwordEncoder.encode("3456"),"3456",true, null);
//        testUser = userRepository.save(testUser);
//        testUser1 = userRepository.save(testUser1);
//        testUser2 = userRepository.save(testUser2);
//
//    }
//}