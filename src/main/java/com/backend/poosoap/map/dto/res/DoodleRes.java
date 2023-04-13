package com.backend.poosoap.map.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoodleRes {

    private Long id;

    private int doodlesCount;

    private Double latitude;

    private Double longitude;

    @Builder
    public DoodleRes(Long id, int doodlesCount, Double latitude, Double longitude) {
        this.id = id;
        this.doodlesCount = doodlesCount;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
