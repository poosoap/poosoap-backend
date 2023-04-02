package com.backend.poosoap.map.service.impl;

import com.backend.poosoap.common.exception.NotFoundException;
import com.backend.poosoap.map.dto.req.Location;
import com.backend.poosoap.map.dto.req.ToiletReq;
import com.backend.poosoap.map.dto.res.ToiletsRes;
import com.backend.poosoap.map.entity.Toilet;
import com.backend.poosoap.map.repository.impl.ToiletRepositoryCustomImpl;
import com.backend.poosoap.map.repository.ToiletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ToiletServiceImplTest {

    private final static String NOT_FOUND_ERROR = "찾을 수 없습니다.";

    @Autowired
    private ToiletServiceImpl toiletService;

    @Autowired
    private ToiletRepository toiletRepository;

    @Autowired
    private ToiletRepositoryCustomImpl gymLocationRepository;

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

    @Test
    @DisplayName("1km 내에 있는 화장실 찾기")
    void findByToiletWithInOne() {
        //given
        saveSampleData();
        Location location = new Location(37.483145, 126.918987);

        //when
        ToiletsRes byToilet = toiletService.findByToilet(location);

        //then
        assertEquals(2, byToilet.getSize());
        assertEquals("서울 관악구 조원로 142", byToilet.getToilets().get(0).getAddr());
    }

    private void saveSampleData() {
        List<Toilet> toilets = new ArrayList<>();

        // WKTReader를 통해 WKT를 실제 타입으로 변환합니다.
        String pointWKT = String.format("POINT(%s %s)", "37.485762", "126.918146");
        Point point = null;
        try {
            point = (Point) new WKTReader().read(pointWKT);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Toilet toilet1 = Toilet.builder()
                .addr("서울 관악구 조원로 142")
                .point(point)
                .build();

        toilets.add(toilet1);

        // WKTReader를 통해 WKT를 실제 타입으로 변환합니다.
        pointWKT = String.format("POINT(%s %s)", "37.482768", "126.915493");
        point = null;
        try {
            point = (Point) new WKTReader().read(pointWKT);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Toilet toilet2 = Toilet.builder()
                .addr("서울 관악구 남부순환로 1485")
                .point(point)
                .build();

        toilets.add(toilet2);

        toiletRepository.saveAll(toilets);
    }

}