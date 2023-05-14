package com.backend.poosoap.map.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Builder;
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

    @Builder
    public DoodleReactions(Long id, Doodle doodle, String userId, int likeCount, int sympathy_count) {
        this.id = id;
        this.doodle = doodle;
        this.userId = userId;
        this.likeCount = likeCount;
        this.sympathy_count = sympathy_count;
    }
}
