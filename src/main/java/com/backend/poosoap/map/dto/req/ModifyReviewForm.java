package com.backend.poosoap.map.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyReviewForm {

    @NotNull(message = "수정하려는 리뷰 key 값은 필수 입니다.")
    @JsonProperty(index = 1)
    private Long Id;

    @NotBlank(message = "수정하려는 리뷰 내용은 필수 입니다.")
    private String content;

    @NotBlank(message = "리뷰를 수정하기 위해선 로그인을 해주세요.")
    private String writer;

    @NotNull
    private Double starRating;

    @Builder
    public ModifyReviewForm(Long id, String content, String writer, Double starRating) {
        Id = id;
        this.content = content;
        this.writer = writer;
        this.starRating = starRating;
    }
}
