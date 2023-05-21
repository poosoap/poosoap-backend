package com.backend.poosoap.map.controller;

import com.backend.poosoap.map.dto.req.ModifyReviewForm;
import com.backend.poosoap.map.dto.req.SaveReviewForm;
import com.backend.poosoap.map.entity.Review;
import com.backend.poosoap.map.entity.Toilet;
import com.backend.poosoap.map.repository.ReviewRepository;
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
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ToiletRepository toiletRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private Toilet toilet;

    @BeforeEach
    void cleanUp() {
        reviewRepository.deleteAll();
    }

    @Test
    @DisplayName("리뷰 등록 api 테스트")
    void createdReview() throws Exception {
        //given
        saveToilet();

        SaveReviewForm req = SaveReviewForm.builder()
                .toiletId(toilet.getId())
                .writer("lee")
                .content("화장실 너무 좋아요, 깨끗하고 만족해요.")
                .starRating(5.0)
                .build();

        String json = objectMapper.writeValueAsString(req);

        //expected
        mockMvc.perform(post("/api/v1/toilet/review")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 수정 api 테스트")
    void modifyReview() throws Exception {
        //given
        saveToilet();
        Review review = Review.builder()
                .toilet(toilet)
                .writer("lee")
                .content("화장실 너무 좋아요, 깨끗하고 만족해요.")
                .starRating(5.0)
                .build();

        Long reviewId = reviewRepository.save(review).getId();

        ModifyReviewForm form = ModifyReviewForm.builder()
                .id(reviewId)
                .writer("lee")
                .content("사실 별로에요")
                .starRating(1.0)
                .build();

        String json = objectMapper.writeValueAsString(form);

        //expected
        mockMvc.perform(patch("/api/v1/toilet/review")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재 하지 않는 리뷰 수정 api 테스트")
    void modifyReviewError() throws Exception {
        //given
        Long reviewId = 10L;

        ModifyReviewForm form = ModifyReviewForm.builder()
                .id(reviewId)
                .writer("lee")
                .content("사실 별로에요")
                .starRating(1.0)
                .build();

        String json = objectMapper.writeValueAsString(form);

        //expected
        mockMvc.perform(patch("/api/v1/toilet/review")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 삭제 api 테스트")
    void deleteReview() throws Exception {
        //given
        saveToilet();

        Review review = Review.builder()
                .toilet(toilet)
                .writer("lee")
                .content("화장실 너무 좋아요, 깨끗하고 만족해요.")
                .starRating(5.0)
                .build();

        Long reviewId = reviewRepository.save(review).getId();

        //expected
        mockMvc.perform(delete("/api/v1/toilet/review/{reviewId}", reviewId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 리뷰 삭제 api 테스트")
    void deleteReviewError() throws Exception {
        //given
        Long id = 10L;

        //expected
        mockMvc.perform(delete("/api/v1/toilet/review/{reviewId}", id)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("화장실의 리뷰 전체 조회")
    void findByReviewToToilet() throws Exception {
        //given
        saveSampleData();

        //expected
        mockMvc.perform(get("/api/v1/toilet/review")
                        .param("toiletId", String.valueOf(toilet.getId()))
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("화장실에 있는 리뷰 상세 보기")
    void findByReview() throws Exception {
        //given
        saveToilet();

        Review review = Review.builder()
                .toilet(toilet)
                .writer("lee")
                .content("화장실 너무 좋아요, 깨끗하고 만족해요.")
                .starRating(5.0)
                .build();

        Long reviewId = reviewRepository.save(review).getId();

        //expected
        mockMvc.perform(get("/api/v1/toilet/review/{reviewId}", reviewId)
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

        toilet = Toilet.builder()
                .addr("서울 관악구 조원로 142")
                .point(point)
                .build();

        toilets.add(toilet);
        toiletRepository.saveAll(toilets);

        // 낙서장 샘플 데이터
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Review review1 = Review.builder()
                    .toilet(toilet)
                    .writer("writer1" + i)
                    .content("test1" + i)
                    .starRating((double) i)
                    .build();

            reviews.add(review1);
        }
        reviewRepository.saveAll(reviews);
    }

}