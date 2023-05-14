package com.backend.poosoap.map.entity;

import com.backend.poosoap.map.dto.req.ModifyReviewForm;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Toilet toilet;

    private String content;

    private String writer;

    @Column(columnDefinition = "int default 0")
    private Double starRating;

    @Temporal(TemporalType.DATE)
    private Date regDate;

    @Temporal(TemporalType.DATE)
    private Date modifyDate;

    @Builder
    public Review(Long id, Toilet toilet, String content, String writer, Double starRating) {
        this.id = id;
        this.toilet = toilet;
        this.content = content;
        this.writer = writer;
        this.starRating = starRating;
        this.regDate = new Date();
    }

    public void modify(ModifyReviewForm modifyReviewForm) {
        this.content = modifyReviewForm.getContent();
        this.starRating = modifyReviewForm.getStarRating();
        this.modifyDate = new Date();
    }
}
