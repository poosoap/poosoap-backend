package com.backend.poosoap.map.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoodleRes {

    private int doodlesCount;

    private Double latitude;

    private Double longitude;

    @Builder
    public DoodleRes(int doodlesCount, Double latitude, Double longitude) {
        this.doodlesCount = doodlesCount;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
