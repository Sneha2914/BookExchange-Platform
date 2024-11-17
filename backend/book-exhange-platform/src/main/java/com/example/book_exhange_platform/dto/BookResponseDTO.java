package com.example.book_exhange_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class BookResponseDTO {

    private Long id;
    private String title;
    private String author;
    private String genre;
    private String location;
    private String condition;
    private Boolean availability;
}
