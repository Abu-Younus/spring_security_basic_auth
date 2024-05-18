package com.younus.springsecuritybasicauth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String category;
    private String author;
    private String publisher;
    private Long isbn;
    private String edition;
    private int numberOfPages;
    private double price;
    private String country;
    private String language;
    private String image;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Date createdAt;
    private Date updatedAt;
}
