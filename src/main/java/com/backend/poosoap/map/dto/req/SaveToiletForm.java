package com.backend.poosoap.map.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveToiletForm {

    private String addr;

    private Location location;

    @Builder
    public SaveToiletForm(String addr, Location location) {
        this.addr = addr;
        this.location = location;
    }
}
