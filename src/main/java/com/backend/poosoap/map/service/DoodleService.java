package com.backend.poosoap.map.service;

import com.backend.poosoap.map.dto.req.Location;
import com.backend.poosoap.map.dto.req.ModifyDoodlesForm;
import com.backend.poosoap.map.dto.req.SaveDoodlesForm;
import com.backend.poosoap.map.dto.res.DoodlesRes;

public interface DoodleService {

    // 낙서장 저장
    Long saveDoodles(SaveDoodlesForm saveDoodlesForm);

    // 낙서장 찾기
    DoodlesRes findByDoodles(Location location);

    // 낙서장 수정
    Long modifyDoodles(ModifyDoodlesForm modifyDoodlesForm);

    // 낙서장 삭제
    Long deleteDoodles(Long id);
}
