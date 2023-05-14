package com.backend.poosoap.map.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReactionRes {

    private Long doodleId;

    private String userId;

    private int likeCount;

    private int loveCount;

    @Builder
    public ReactionRes(Long doodleId, String userId, int likeCount, int loveCount) {
        this.doodleId = doodleId;
        this.userId = userId;
        this.likeCount = likeCount;
        this.loveCount = loveCount;
    }
}
