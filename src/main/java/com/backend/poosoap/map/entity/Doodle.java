package com.backend.poosoap.map.entity;

import com.backend.poosoap.map.dto.req.ModifyDoodleForm;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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

    @Builder
    public Doodle(Long id, Toilet toilet, String content, String writer, int totalLikeCount, int totalSympathyCount, boolean isAnonymous) {
        this.id = id;
        this.toilet = toilet;
        this.content = content;
        this.writer = writer;
        this.totalLikeCount = totalLikeCount;
        this.totalSympathyCount = totalSympathyCount;
        this.isAnonymous = isAnonymous;
    }

    public void modify(ModifyDoodleForm modifyDoodleForm) {
        this.content = modifyDoodleForm.getContent();
        this.writer = modifyDoodleForm.getWriter();
        this.isAnonymous = modifyDoodleForm.isAnonymous();
    }
}
