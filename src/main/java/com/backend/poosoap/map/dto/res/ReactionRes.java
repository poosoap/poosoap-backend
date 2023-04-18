package com.backend.poosoap.map.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReactionRes {

    private Long doodleId;

    private String userId;

    private int likeCount;

    private int loveCount;
}
