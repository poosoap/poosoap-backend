package com.backend.poosoap.map.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyDoodlesForm {

    @NotNull(message = "수정하려는 낙서장 key 값은 필수 입니다.")
    private Long Id;

    @NotBlank(message = "등록하려는 낙서 내용은 필수 입니다.")
    private String content;

    private String writer;

    @NotBlank(message = "익명 여부 값은 필수 입니다.")
    private boolean isAnonymous;

    @Builder
    public ModifyDoodlesForm(Long id, String content, String writer, boolean isAnonymous) {
        Id = id;
        this.content = content;
        this.writer = writer;
        this.isAnonymous = isAnonymous;
    }
}
