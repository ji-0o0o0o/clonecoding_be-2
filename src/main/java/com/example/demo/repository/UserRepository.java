package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

//spring data jpa
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorityEntity")
    Optional<User> findOneWithAuthorityEntityByUserName(String userName);

    Optional<User> findOneWithAuthorityEntityByUserEmail(String userEmail);

    Optional<User> findByUserEmail(String userEmail);

    Optional<User> findByUserId(String username);

}