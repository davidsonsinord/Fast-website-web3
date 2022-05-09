package com.davidson.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleDTO {

    private String path;
    private String title;
    private String date;
    private String author;
    private String cover;
    private String markdown;

}