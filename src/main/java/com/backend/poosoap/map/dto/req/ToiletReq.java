package com.backend.poosoap.map.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ToiletReq {

    private String addr;

    private Location location;

    @Builder
    public ToiletReq(String addr, Location location) {
        this.addr = addr;
        this.location = location;
    }
}
