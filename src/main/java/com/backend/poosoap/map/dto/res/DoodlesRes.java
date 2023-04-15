package com.backend.poosoap.map.dto.res;

import lombok.Getter;

import java.util.List;

@Getter
public class DoodlesRes {

    List<DoodleRes> doodles;

    public DoodlesRes(List<DoodleRes> doodles) {
        this.doodles = doodles;
    }

    public int getSize() {
        return this.doodles.size();
    }
}
