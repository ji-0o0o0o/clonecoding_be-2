package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

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
    private Articles articles;

    private Long articlesImageId;



}
