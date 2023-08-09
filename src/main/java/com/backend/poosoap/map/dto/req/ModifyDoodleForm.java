package com.backend.poosoap.map.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyDoodleForm {

    @NotNull(message = "수정 하려는 낙서장 key 값은 필수 입니다.")
    @JsonProperty(index = 1)
    private Long Id;

    @NotBlank(message = "등록 하려는 낙서 내용은 필수 입니다.")
    private String content;

    private String writer;

    @NotNull(message = "익명 여부 값은 필수 입니다.")
    private boolean isAnonymous;

    @Builder
    public ModifyDoodleForm(Long id, String content, String writer, boolean isAnonymous) {
        Id = id;
        this.content = content;
        this.writer = writer;
        this.isAnonymous = isAnonymous;
    }
}
