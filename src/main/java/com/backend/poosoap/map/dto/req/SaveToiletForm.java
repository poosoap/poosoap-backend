package com.backend.poosoap.map.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveToiletForm {

    @NotBlank(message = "등록하려는 화장실 주소 값은 필수 입니다.")
    private String addr;

    @NotBlank(message = "등록하려는 화장실 위치 값은 필수 입니다.")
    private Location location;

    @Builder
    public SaveToiletForm(String addr, Location location) {
        this.addr = addr;
        this.location = location;
    }
}
