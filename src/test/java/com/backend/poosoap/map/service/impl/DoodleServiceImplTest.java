package com.backend.poosoap.map.service.impl;

import com.backend.poosoap.common.exception.NotFoundException;
import com.backend.poosoap.map.dto.req.*;
import com.backend.poosoap.map.dto.res.DoodlesRes;
import com.backend.poosoap.map.entity.Doodle;
import com.backend.poosoap.map.entity.Toilet;
import com.backend.poosoap.map.repository.DoodleRepository;
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
class DoodleServiceImplTest {

    private final static String NOT_FOUND_ERROR = "찾을 수 없습니다.";

    @Autowired
    private ToiletRepository toiletRepository;

    @Autowired
    private DoodleServiceImpl doodleService;

    @Autowired
    private DoodleRepository doodleRepository;

    private Toilet toilet;

    @BeforeEach
    void cleanUp() {
        doodleRepository.deleteAll();
    }

    @Test
    @DisplayName("낙서장 저장 테스트")
    void saveDoodle() {
        //given
        saveToilet();

        SaveDoodlesForm req = SaveDoodlesForm.builder()
                .toiletId(toilet.getId())
                .content("test")
                .writer("lee")
                .isAnonymous(true)
                .build();

        //when
        Long doodleId = doodleService.saveDoodles(req);

        Doodle doodle = doodleRepository.findById(doodleId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR));

        //then
        assertEquals("test", doodle.getContent());
        assertEquals("lee", doodle.getWriter());
        assertTrue(doodle.isAnonymous());
    }

    @Test
    @DisplayName("1km 내에 있는 화장실에 있는 낙서 찾기")
    void findByToiletDoodle() {
        //given
        saveSampleData();
        Location location = new Location(1.0, 37.483145, 126.918987);

        //when
        DoodlesRes doodles = doodleService.findByDoodles(location);

        //then
        assertEquals(2, doodles.getSize());
        assertEquals(37.485762, doodles.getDoodles().get(0).getLatitude());
        assertEquals(126.918146, doodles.getDoodles().get(0).getLongitude());
    }

    @Test
    @DisplayName("낙서장 수정 테스트")
    void modifyDoodle() {
        //given
        saveToilet();

        Doodle doodle = Doodle.builder()
                .toilet(toilet)
                .content("test")
                .writer("lee")
                .isAnonymous(true)
                .build();
        Doodle saveDoodle = doodleRepository.save(doodle);

        ModifyDoodleForm modifyDoodleForm = ModifyDoodleForm.builder()
                .id(saveDoodle.getId())
                .writer("kim")
                .content("modify")
                .build();

        //when
        Long doodleId = doodleService.modifyDoodles(modifyDoodleForm);

        Doodle modifyDoodle = doodleRepository.findById(doodleId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR));

        //then
        assertEquals("kim", modifyDoodle.getWriter());
        assertEquals("modify", modifyDoodle.getContent());
    }

    @Test
    @DisplayName("존재 하지 않는 낙서장 수정시 에러 발생")
    void modifyDoodleError() {
        //given
        ModifyDoodleForm modifyDoodleForm = ModifyDoodleForm.builder()
                .id(1L)
                .writer("kim")
                .content("modify")
                .build();

        // expected
        assertThrows(NotFoundException.class, () -> {
            doodleService.modifyDoodles(modifyDoodleForm);
        });
    }

    @Test
    @DisplayName("낙서장 삭제 테스트")
    void deleteDoodle() {
        //given
        saveToilet();

        Doodle doodle = Doodle.builder()
                .toilet(toilet)
                .content("test")
                .writer("lee")
                .isAnonymous(true)
                .build();
        Doodle saveDoodle = doodleRepository.save(doodle);

        //when
        Long doodleId = doodleService.deleteDoodles(saveDoodle.getId());

        //then
        assertEquals(0, doodleRepository.count());
    }

    @Test
    @DisplayName("존재 하지 않는 화장실 삭제 테스트시 에러발생")
    void deleteToiletError() {
        //given
        Long id = 10L;

        //expected
        assertThrows(NotFoundException.class, () -> {
            doodleService.deleteDoodles(id);
        });
    }

    private void saveToilet() {
        // WKTReader를 통해 WKT를 실제 타입으로 변환합니다.
        String pointWKT = String.format("POINT(%s %s)", "37.485762", "126.918146");
        Point point = null;
        try {
            point = (Point) new WKTReader().read(pointWKT);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        toilet = Toilet.builder()
                .addr("서울 관악구 조원로 142")
                .point(point)
                .build();

        toiletRepository.save(toilet);
    }

    private void saveSampleData() {
        // 화장실 샘플 데이터
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

        // 낙서장 샘플 데이터
        List<Doodle> doodles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Doodle doodle1 = Doodle.builder()
                    .toilet(toilet1)
                    .writer("writer1" + i)
                    .content("test1" + i)
                    .isAnonymous(true)
                    .build();

            Doodle doodle2 = Doodle.builder()
                    .toilet(toilet2)
                    .writer("writer2" + i)
                    .content("test2" + i)
                    .isAnonymous(true)
                    .build();

            doodles.add(doodle1);
            doodles.add(doodle2);
        }
        doodleRepository.saveAll(doodles);
    }

}