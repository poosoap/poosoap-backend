package com.backend.poosoap.map.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyToiletForm {

    @NotNull(message = "변경 하려는 화장실 id 값은 필수 입니다.")
    private Long id;

    @NotBlank(message = "변경 하려는 화장실 주소 값은 필수 입니다.")
    private String addr;

    @NotNull(message = "변경 하려는 화장실 위치 값은 필수 입니다.")
    private Location location;

    @Builder
    public ModifyToiletForm(Long id, String addr, Location location) {
        this.id = id;
        this.addr = addr;
        this.location = location;
    }
}
