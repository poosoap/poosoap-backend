package com.backend.poosoap.map.service.impl;

import com.backend.poosoap.common.exception.NotFoundException;
import com.backend.poosoap.map.dto.req.ModifyReviewForm;
import com.backend.poosoap.map.dto.req.SaveReviewForm;
import com.backend.poosoap.map.dto.res.ReviewRes;
import com.backend.poosoap.map.entity.Review;
import com.backend.poosoap.map.entity.Toilet;
import com.backend.poosoap.map.repository.ReviewRepository;
import com.backend.poosoap.map.repository.ToiletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ReviewServiceImplTest {

    private final static String NOT_FOUND_ERROR = "찾을 수 없습니다.";

    @Autowired
    private ToiletRepository toiletRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewServiceImpl reviewService;

    private Toilet toilet;

    @BeforeEach
    void cleanUp() {
        reviewRepository.deleteAll();
    }

    @Test
    @DisplayName("리뷰 저장 테스트")
    void saveReview() {
        //given
        saveToilet();

        SaveReviewForm req = SaveReviewForm.builder()
                .toiletId(toilet.getId())
                .writer("lee")
                .content("화장실 너무 좋아요, 깨끗하고 만족해요.")
                .starRating(5.0)
                .build();

        //when
        Long reviewId = reviewService.saveReview(req);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR));

        //then
        assertEquals("화장실 너무 좋아요, 깨끗하고 만족해요.", review.getContent());
        assertEquals("lee", review.getWriter());
        assertEquals(5.0, review.getStarRating());
    }

    @Test
    @DisplayName("화장실에 있는 리뷰 상세 보기")
    void findByReview() {
        //given
        saveToilet();

        SaveReviewForm req = SaveReviewForm.builder()
                .toiletId(toilet.getId())
                .writer("lee")
                .content("화장실 너무 좋아요, 깨끗하고 만족해요.")
                .starRating(5.0)
                .build();

        Long reviewId = reviewService.saveReview(req);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR));

        //when
        ReviewRes reviewRes = reviewService.findByReview(review.getId());

        //then
        assertEquals("화장실 너무 좋아요, 깨끗하고 만족해요.", reviewRes.getContent());
        assertEquals("lee", reviewRes.getWriter());
        assertEquals(5.0, review.getStarRating());
    }

    @Test
    @DisplayName("화장실에 있는 리뷰들 상세 보기")
    void findByReviewToToilet() {
        //given
        saveSampleData();

        //when
        Sort pageableSort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 10, pageableSort);
        Page<ReviewRes> reviewRes = reviewService.findByReviewToToilet(toilet.getId(), pageable);

        //then
        assertEquals(5, reviewRes.getSize());
    }

    @Test
    @DisplayName("리뷰 수정 테스트")
    void modifyReview() {
        //given
        saveToilet();

        SaveReviewForm req = SaveReviewForm.builder()
                .toiletId(toilet.getId())
                .writer("lee")
                .content("화장실 너무 좋아요, 깨끗하고 만족해요.")
                .starRating(5.0)
                .build();

        Long reviewId = reviewService.saveReview(req);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR));

        ModifyReviewForm form = ModifyReviewForm.builder()
                .id(review.getId())
                .writer("lee")
                .content("사실 별로에요")
                .starRating(1.0)
                .build();

        //when
        Long modifyReviewId = reviewService.modifyReview(form);

        Review modifyReview = reviewRepository.findById(modifyReviewId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR));

        //then
        assertEquals("lee", modifyReview.getWriter());
        assertEquals("사실 별로에요", modifyReview.getContent());
        assertEquals(1.0, modifyReview.getStarRating());
    }

    @Test
    @DisplayName("존재 하지 않는 리뷰 수정시 에러 발생")
    void modifyReviewError() {
        //given
        ModifyReviewForm form = ModifyReviewForm.builder()
                .id(10L)
                .writer("lee")
                .content("사실 별로에요")
                .starRating(1.0)
                .build();

        // expected
        assertThrows(NotFoundException.class, () -> {
            reviewService.modifyReview(form);
        });
    }

    @Test
    @DisplayName("리뷰 삭제 테스트")
    void deleteReview() {
        //given
        saveToilet();

        SaveReviewForm req = SaveReviewForm.builder()
                .toiletId(toilet.getId())
                .writer("lee")
                .content("화장실 너무 좋아요, 깨끗하고 만족해요.")
                .starRating(5.0)
                .build();

        Long reviewId = reviewService.saveReview(req);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR));

        //when
        Long doodleId = reviewService.deleteReview(review.getId());

        //then
        assertEquals(0, reviewRepository.count());
    }

    @Test
    @DisplayName("존재 하지 않는 리뷰 삭제 테스트시 에러발생")
    void deleteReviewError() {
        //given
        Long id = 10L;

        //expected
        assertThrows(NotFoundException.class, () -> {
            reviewService.deleteReview(id);
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