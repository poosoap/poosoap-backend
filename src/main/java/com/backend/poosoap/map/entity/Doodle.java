package com.backend.poosoap.map.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Doodle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Toilet toilet;

    private String content;

    private String writer;

    @Column(columnDefinition = "int default 0")
    private int totalLikeCount;

    @Column(columnDefinition = "int default 0")
    private int totalSympathyCount;

    private boolean isAnonymous;
}
