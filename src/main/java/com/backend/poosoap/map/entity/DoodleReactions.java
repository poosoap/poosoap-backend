package com.backend.poosoap.map.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class DoodleReactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Doodle doodle;

    private String userId;

    @Column(columnDefinition = "int default 0")
    private int likeCount;

    @Column(columnDefinition = "int default 0")
    private int sympathy_count;

}
