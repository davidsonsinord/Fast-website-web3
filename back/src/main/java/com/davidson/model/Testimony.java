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
public class Testimony {

    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String image;
    private String description;

}
