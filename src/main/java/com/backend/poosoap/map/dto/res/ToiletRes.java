package com.backend.poosoap.map.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ToiletRes {

    private Long id;

    private String addr;

    private String start_time;

    private String end_time;

    private Double latitude;

    private Double longitude;

    @Builder
    public ToiletRes(Long id, String addr, String start_time, String end_time, Double latitude, Double longitude) {
        this.id = id;
        this.addr = addr;
        this.start_time = start_time;
        this.end_time = end_time;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
