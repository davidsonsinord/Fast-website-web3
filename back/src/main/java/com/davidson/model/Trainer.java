package com.davidson.model;

import lombok.*;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Entity trainer present in the database
 */
@NodeEntity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Trainer {

    @Id
    @GeneratedValue
    private Long id;
    private String image;
    private String nom;
    private String description;

    }
