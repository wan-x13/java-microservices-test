package com.wanlab.microapi1.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ArticleDto {
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String body;
    @NotNull
    private LocalDate publishedAt;
    @NotNull
    private  int authorId ;
}
