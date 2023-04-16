package com.backend.poosoap.map.controller;

import com.backend.poosoap.map.dto.req.*;
import com.backend.poosoap.map.entity.Doodle;
import com.backend.poosoap.map.entity.Toilet;
import com.backend.poosoap.map.repository.DoodleRepository;
import com.backend.poosoap.map.repository.ToiletRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DoodleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ToiletRepository toiletRepository;

    @Autowired
    private DoodleRepository doodleRepository;

    private Toilet toilet;

    @BeforeEach
    void cleanUp() {
        doodleRepository.deleteAll();
    }

    @Test
    @DisplayName("낙서장 등록 api 테스트")
    void createdDoodle() throws Exception {
        //given
        saveToilet();

        SaveDoodlesForm req = SaveDoodlesForm.builder()
                .toiletId(toilet.getId())
                .content("test")
                .writer("lee")
                .isAnonymous(true)
                .build();

        String json = objectMapper.writeValueAsString(req);

        //expected
        mockMvc.perform(post("/api/v1/toilet/doodle")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("낙서장 수정 api 테스트")
    void modifyDoodle() throws Exception {
        //given
        saveToilet();
        Doodle doodle = Doodle.builder()
                .toilet(toilet)
                .content("test")
                .writer("lee")
                .isAnonymous(true)
                .build();

        Long doodleId = doodleRepository.save(doodle).getId();

        ModifyDoodleForm modifyDoodleForm = ModifyDoodleForm.builder()
                .id(doodleId)
                .writer("kim")
                .content("modify")
                .isAnonymous(true)
                .build();

        String json = objectMapper.writeValueAsString(modifyDoodleForm);

        //expected
        mockMvc.perform(patch("/api/v1/toilet/doodle")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재 하지 않는 낙서장 수정 api 테스트")
    void modifyDoodleError() throws Exception {
        //given
        Long doodleId = 10L;

        ModifyDoodleForm modifyDoodleForm = ModifyDoodleForm.builder()
                .id(doodleId)
                .writer("kim")
                .content("modify")
                .isAnonymous(true)
                .build();

        String json = objectMapper.writeValueAsString(modifyDoodleForm);

        //expected
        mockMvc.perform(patch("/api/v1/toilet/doodle")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("낙서장 삭제 api 테스트")
    void deleteDoodle() throws Exception {
        //given
        saveToilet();

        Doodle doodle = Doodle.builder()
                .toilet(toilet)
                .content("test")
                .writer("lee")
                .isAnonymous(true)
                .build();
        Doodle saveDoodle = doodleRepository.save(doodle);

        //expected
        mockMvc.perform(delete("/api/v1/toilet/doodle/{doodleId}", saveDoodle.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 낙서장 삭제 api 테스트")
    void deleteDoodleError() throws Exception {
        //given
        Long id = 10L;

        //expected
        mockMvc.perform(delete("/api/v1/toilet/doodle/{doodleId}", id)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("1km 내에 있는 화장실의 낙서장 찾기")
    void findByToiletDoodle() throws Exception {
        //given
        saveSampleData();

        //expected
        mockMvc.perform(get("/api/v1/toilet/doodle/{radius}/{latitude}/{longitude}", 1.0, "37.483145", "126.918987")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
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