package com.backend.poosoap.map.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyToiletForm {

    private Long id;

    private String addr;

    private Location location;

    @Builder
    public ModifyToiletForm(Long id, String addr, Location location) {
        this.id = id;
        this.addr = addr;
        this.location = location;
    }
}
