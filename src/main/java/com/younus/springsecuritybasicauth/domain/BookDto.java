package com.younus.springsecuritybasicauth.domain;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    @NotEmpty(message = "The title is required!")
    private String title;

    @NotEmpty(message = "The category is required!")
    private String category;

    @NotEmpty(message = "The author is required!")
    private String author;

    @NotEmpty(message = "The publisher is required!")
    private String publisher;

    @NotNull(message = "The publisher is required!")
    private Long isbn;

    @NotEmpty(message = "The edition is required!")
    private String edition;

    @Min(0)
    private int numberOfPages;

    @Min(0)
    private double price;

    @NotEmpty(message = "The country is required!")
    private String country;

    @NotEmpty(message = "The language is required!")
    private String language;

    private MultipartFile image;

    @NotEmpty(message = "The description is required!")
    private String description;
}
