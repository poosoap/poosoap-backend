package com.backend.poosoap.map.dto.res;

import lombok.Getter;

import java.util.List;

@Getter
public class ToiletsRes {

    List<ToiletRes> toilets;

    public ToiletsRes(List<ToiletRes> toilets) {
        this.toilets = toilets;
    }

    public int getSize() {
        return this.toilets.size();
    }
}
