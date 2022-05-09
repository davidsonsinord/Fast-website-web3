package com.davidson.model;

import lombok.*;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

/**
 * Entity domain present in the database
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Article {

    @Id
    @GeneratedValue
    private Long id;
    private String path;
    private String title;
    private String date;
    private String author;
    private String cover;
    private String markdown;
}
