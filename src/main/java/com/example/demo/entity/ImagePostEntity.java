package com.example.demo.entity;

import com.example.demo.dto.ImageDetailResponseDto;
import com.example.demo.dto.MyPageDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
@Builder
public class ImagePostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long imageId;

    @Column
    private String image;

    @Column
    @JsonIgnore
    private String userName;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="articles_Id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Articles articles;

    private Long articlesImageId;




}
