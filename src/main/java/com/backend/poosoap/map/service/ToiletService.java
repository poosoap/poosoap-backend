package com.backend.poosoap.map.service;

import com.backend.poosoap.map.dto.req.Location;
import com.backend.poosoap.map.dto.req.ModifyToiletForm;
import com.backend.poosoap.map.dto.req.SaveToiletForm;
import com.backend.poosoap.map.dto.res.ToiletsRes;

public interface ToiletService {

    // 화장실 저장
    Long saveToilet(SaveToiletForm saveToiletForm);

    // 화장실 찾기
    ToiletsRes findByToilet(Location location);

    // 화장실 수정
    Long modifyToilet(ModifyToiletForm modifyToiletForm);

    // 화장실 삭제
    Long deleteToilet(Long id);
}
