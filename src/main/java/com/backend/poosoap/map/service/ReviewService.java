package com.backend.poosoap.map.service;

import com.backend.poosoap.map.dto.req.ModifyReviewForm;
import com.backend.poosoap.map.dto.req.SaveReviewForm;
import com.backend.poosoap.map.dto.res.ReviewRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    // 리뷰 저장
    Long saveReview(SaveReviewForm saveReviewForm);

    // 리뷰 찾기
    Page<ReviewRes> findByReviewToToilet(Long toiletId, Pageable pageable);

    // 리뷰 상세 보기
    ReviewRes findByReview(Long reviewId);

    // 리뷰 수정
    Long modifyReview(ModifyReviewForm modifyReviewForm);

    // 리뷰 삭제
    Long deleteReview(Long id);
}
