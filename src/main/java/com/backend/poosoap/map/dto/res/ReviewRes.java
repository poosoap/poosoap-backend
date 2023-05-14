package com.backend.poosoap.map.dto.res;

import com.backend.poosoap.map.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewRes {

    private Long toiletId;

    private String writer;

    private String content;

    private String regDate;

    private Double starRating;

    @Builder
    public ReviewRes(Long toiletId, String writer, String content, String regDate, Double starRating) {
        this.toiletId = toiletId;
        this.writer = writer;
        this.content = content;
        this.regDate = regDate;
        this.starRating = starRating;
    }

    public static Page<ReviewRes> reviewToReviewRes(Page<Review> reviews) {

        List<ReviewRes> resReview = new ArrayList<>();
        for (Review review : reviews) {
            resReview.add(ReviewRes.builder()
                    .toiletId(review.getToilet().getId())
                    .content(review.getContent())
                    .writer(review.getWriter())
                    .starRating(review.getStarRating())
                    .regDate(review.getRegDate().toString())
                    .build());
        }

        return new PageImpl<>(resReview);
    }
}
