package com.backend.poosoap.map.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveDoodlesForm {

    @NotNull(message = "등록하려는 화장실 key 값은 필수 입니다.")
    private Long toiletId;

    @NotBlank(message = "등록하려는 낙서 내용은 필수 입니다.")
    private String content;

    private String writer;

    @NotBlank(message = "익명 여부 값은 필수 입니다.")
    private boolean isAnonymous;

    @Builder
    public SaveDoodlesForm(Long toiletId, String content, String writer, boolean isAnonymous) {
        this.toiletId = toiletId;
        this.content = content;
        this.writer = writer;
        this.isAnonymous = isAnonymous;
    }
}
