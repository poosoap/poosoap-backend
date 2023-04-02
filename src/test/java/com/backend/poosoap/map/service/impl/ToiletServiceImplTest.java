package com.backend.poosoap.map.service.impl;

import com.backend.poosoap.common.exception.NotFoundException;
import com.backend.poosoap.map.dto.req.Location;
import com.backend.poosoap.map.dto.req.ToiletReq;
import com.backend.poosoap.map.entity.Toilet;
import com.backend.poosoap.map.repository.ToiletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ToiletServiceImplTest {

    private final static String NOT_FOUND_ERROR = "찾을 수 없습니다.";

    @Autowired
    private ToiletServiceImpl toiletService;

    @Autowired
    private ToiletRepository toiletRepository;

    @BeforeEach
    void cleanUp() {
//        toiletRepository.deleteAll();
    }

    @Test
    @DisplayName("화장실 저장 테스트")
    void saveToilet() {
        //given
        ToiletReq req = ToiletReq.builder()
                .addr("test")
                .location(new Location(32.123, 127.123))
                .build();

        //when
        Long toiletId = toiletService.saveToilet(req);

        Toilet saveToilet = toiletRepository.findById(toiletId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR));

        //then
        assertEquals("test", saveToilet.getAddr());
    }

}