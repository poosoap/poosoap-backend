package com.backend.poosoap.map.dto.res;

import lombok.Getter;

import java.util.List;

@Getter
public class FindDoodles {

    List<FindDoodle> findDoodles;

    public FindDoodles(List<FindDoodle> findDoodles) {
        this.findDoodles = findDoodles;
    }

    public int getSize() {
        return this.findDoodles.size();
    }
}
