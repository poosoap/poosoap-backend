package com.backend.poosoap.map.service;

import com.backend.poosoap.map.dto.req.Location;
import com.backend.poosoap.map.dto.req.ToiletReq;
import com.backend.poosoap.map.dto.res.ToiletsRes;

public interface ToiletService {

    // 화장실 저장
    Long saveToilet(ToiletReq toiletReq);

    // 화장실 찾기
    ToiletsRes findByToilet(Location location);
}
