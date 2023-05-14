package com.backend.poosoap.map.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindDoodle {

    private String content;

    private String writer;

    private String regDate;

    @Builder
    public FindDoodle(String content, String writer, String regDate) {
        this.content = content;
        this.writer = writer;
        this.regDate = regDate;
    }

}
