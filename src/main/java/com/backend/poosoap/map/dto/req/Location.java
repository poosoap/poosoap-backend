package com.backend.poosoap.map.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Location {

    @NotBlank(message = "반경 값은 필수 입니다.")
    private Double radius;

    @NotBlank(message = "위도값은 필수 입니다.")
    private Double latitude;

    @NotBlank(message = "경도값은 필수 입니다.")
    private Double longitude;

    public Location(Double radius, Double latitude, Double longitude) {
        this.radius = radius;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Double latitude, Double longitude) {
        this.radius = 1.0;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(String latitude, String longitude) {
        this.radius = 1.0;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
    }

    public Location(String radius, String latitude, String longitude) {
        this.radius = Double.parseDouble(radius);;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
    }
}
