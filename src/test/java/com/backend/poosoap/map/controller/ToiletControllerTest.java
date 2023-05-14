package com.backend.poosoap.map.controller;

import com.backend.poosoap.map.dto.req.Location;
import com.backend.poosoap.map.dto.req.ModifyToiletForm;
import com.backend.poosoap.map.dto.req.SaveToiletForm;
import com.backend.poosoap.map.entity.Toilet;
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
class ToiletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ToiletRepository toiletRepository;

    @BeforeEach
    void cleanUp() {
        toiletRepository.deleteAll();
    }

    @Test
    @DisplayName("화장실 등록 api 테스트")
    void createdToilet() throws Exception {
        //given
        SaveToiletForm req = SaveToiletForm.builder()
                .addr("test")
                .location(new Location(32.123, 127.123))
                .build();

        String json = objectMapper.writeValueAsString(req);

        //expected
        mockMvc.perform(post("/api/v1/toilet")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("화장실 수정 api 테스트")
    void modifyToilet() throws Exception {
        //given
        Long id = initDataInput();

        ModifyToiletForm modifyToiletForm = ModifyToiletForm.builder()
                .id(id)
                .addr("테스트 수정 화장실")
                .location(new Location("123.12", "321.21"))
                .build();

        String json = objectMapper.writeValueAsString(modifyToiletForm);

        //expected
        mockMvc.perform(patch("/api/v1/toilet")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재 하지 않는 화장실 수정 api 테스트")
    void modifyToiletError() throws Exception {
        //given
        Long id = 10L;

        ModifyToiletForm modifyToiletForm = ModifyToiletForm.builder()
                .id(id)
                .addr("테스트 수정 화장실")
                .location(new Location("123.12", "321.21"))
                .build();

        String json = objectMapper.writeValueAsString(modifyToiletForm);

        //expected
        mockMvc.perform(patch("/api/v1/toilet")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("화장실 수정시 필수 값(주소) 없을때 생기는 api 에러 테스트")
    void modifyToiletAddrError() throws Exception {
        //given
        Long id = initDataInput();

        ModifyToiletForm modifyToiletForm = ModifyToiletForm.builder()
                .id(id)
                .location(new Location("123.12", "321.21"))
                .build();

        String json = objectMapper.writeValueAsString(modifyToiletForm);

        //expected
        mockMvc.perform(patch("/api/v1/toilet")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("화장실 수정시 필수 값(위치) 없을때 생기는 api 에러 테스트")
    void modifyToiletLocationError() throws Exception {
        //given
        Long id = initDataInput();

        ModifyToiletForm modifyToiletForm = ModifyToiletForm.builder()
                .id(id)
                .addr("test addr")
                .build();

        String json = objectMapper.writeValueAsString(modifyToiletForm);

        //expected
        mockMvc.perform(patch("/api/v1/toilet")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("화장실 삭제 api 테스트")
    void deleteToilet() throws Exception {
        //given
        Long id = initDataInput();

        //expected
        mockMvc.perform(delete("/api/v1/toilet/{toiletId}", id)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 화장실 삭제 api 테스트")
    void deleteToiletError() throws Exception {
        //given
        Long id = 10L;

        //expected
        mockMvc.perform(delete("/api/v1/toilet/{toiletId}", id)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("1km 내에 있는 화장실 찾기")
    void findByToiletWithInOne() throws Exception {
        //given
        saveSampleData();

        //expected
        mockMvc.perform(get("/api/v1/toilet")
                        .param("radius", "1.0")
                        .param("latitude", "37.483145")
                        .param("longitude", "126.918987")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private Long initDataInput() {
        String pointWKT = String.format("POINT(%s %s)", 123.123, 234.234);

        Point point = null;
        try {
            point = (Point) new WKTReader().read(pointWKT);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Toilet toilet = Toilet.builder()
                .addr("test")
                .point(point)
                .build();

        return toiletRepository.save(toilet).getId();
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