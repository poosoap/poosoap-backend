package com.backend.poosoap.map.entity;

import com.backend.poosoap.map.dto.req.ModifyDoodleForm;
import com.backend.poosoap.map.dto.req.ReactionType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Doodle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Toilet toilet;

    @OneToMany(mappedBy = "doodle", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<DoodleReactions> reactions;

    private String content;

    private String writer;

    @Column(columnDefinition = "int default 0")
    private int totalLikeCount;

    @Column(columnDefinition = "int default 0")
    private int totalLoveCount;

    private boolean isAnonymous;

    @Temporal(TemporalType.DATE)
    private Date regDate;

    @Temporal(TemporalType.DATE)
    private Date modifyDate;

    @Builder
    public Doodle(Long id, Toilet toilet, String content, String writer, int totalLikeCount, int totalLoveCount, boolean isAnonymous) {
        this.id = id;
        this.toilet = toilet;
        this.content = content;
        this.writer = writer;
        this.totalLikeCount = totalLikeCount;
        this.totalLoveCount = totalLoveCount;
        this.isAnonymous = isAnonymous;
        this.regDate = new Date();
    }

    public void modify(ModifyDoodleForm modifyDoodleForm) {
        this.content = modifyDoodleForm.getContent();
        this.writer = modifyDoodleForm.getWriter();
        this.isAnonymous = modifyDoodleForm.isAnonymous();
        this.modifyDate = new Date();
    }

    public void updateLikeCount(ReactionType reactionType) {
        if (reactionType.getValue() > 0) {
            this.totalLikeCount += reactionType.getValue();
        } else {
            this.totalLikeCount -= reactionType.getValue();
        }
    }

    public void updateLoveCount(ReactionType reactionType) {
        if (reactionType.getValue() > 0) {
            this.totalLoveCount += reactionType.getValue();
        } else {
            this.totalLoveCount -= reactionType.getValue();
        }
    }
}
