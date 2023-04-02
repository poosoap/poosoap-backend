package com.backend.poosoap.map.controller;

import com.backend.poosoap.map.dto.req.Location;
import com.backend.poosoap.map.dto.req.ToiletReq;
import com.backend.poosoap.map.repository.ToiletRepository;
import com.backend.poosoap.map.service.impl.ToiletServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private ToiletServiceImpl toiletService;

    @Autowired
    private ToiletRepository toiletRepository;

    @Test
    @DisplayName("화장실 등록 api 테스트")
    void createdToilet() throws Exception {
        //given
        ToiletReq req = ToiletReq.builder()
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

}