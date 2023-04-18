package com.backend.poosoap.map.dto.req;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReactionForm {

    @NotNull(message = "낙서장 key 값은 필수 입니다.")
    private Long doodleId;

    @NotNull(message = "좋아요를 누른 유자 아이디는 필수 입니다.")
    private String userId;

    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;
}
