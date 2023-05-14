package com.backend.poosoap.map.service;

import com.backend.poosoap.map.dto.req.Location;
import com.backend.poosoap.map.dto.req.ModifyDoodleForm;
import com.backend.poosoap.map.dto.req.ReactionForm;
import com.backend.poosoap.map.dto.req.SaveDoodlesForm;
import com.backend.poosoap.map.dto.res.DoodlesRes;
import com.backend.poosoap.map.dto.res.FindDoodles;
import com.backend.poosoap.map.dto.res.ReactionRes;

public interface DoodleService {

    // 낙서장 저장
    Long saveDoodles(SaveDoodlesForm saveDoodlesForm);

    // 낙서장 찾기
    DoodlesRes findByDoodles(Location location);

    FindDoodles findByDoodle(Long toiletId);

    // 낙서장 수정
    Long modifyDoodles(ModifyDoodleForm modifyDoodleForm);

    // 낙서장 삭제
    Long deleteDoodles(Long id);

    // 좋아요, 공감 추가
    ReactionRes likeLoveCount(ReactionForm reactionForm);
}
