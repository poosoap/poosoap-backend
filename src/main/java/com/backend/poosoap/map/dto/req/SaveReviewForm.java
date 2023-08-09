package com.backend.poosoap.map.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveReviewForm {

    @NotNull(message = "등록하려는 화장실 key 값은 필수 입니다.")
    private Long toiletId;

    @NotBlank(message = "등록 하려는 리뷰 내용은 필수 입니다.")
    private String content;

    @NotBlank(message = "리뷰를 등록 하기 위해선 로그인을 해주세요.")
    private String writer;

    @NotNull(message = "평점은 필수 값 입니다.")
    private Double starRating;

    @Builder
    public SaveReviewForm(Long toiletId, String content, String writer, Double starRating) {
        this.toiletId = toiletId;
        this.content = content;
        this.writer = writer;
        this.starRating = starRating;
    }
}
