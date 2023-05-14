package com.backend.poosoap.map.service.impl;

import com.backend.poosoap.common.exception.NotFoundException;
import com.backend.poosoap.map.dto.req.ModifyReviewForm;
import com.backend.poosoap.map.dto.req.SaveReviewForm;
import com.backend.poosoap.map.dto.res.ReviewRes;
import com.backend.poosoap.map.entity.Review;
import com.backend.poosoap.map.entity.Toilet;
import com.backend.poosoap.map.repository.ReviewRepository;
import com.backend.poosoap.map.repository.ToiletRepository;
import com.backend.poosoap.map.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final static String NOT_FOUND_TOILET_ERROR_MSG = "해당하는 화장실을 찾을 수 없습니다";

    private final static String NOT_FOUND_REVIEW_ERROR_MSG = "해당하는 리뷰를 찾을 수 없습니다";

    private final ToiletRepository toiletRepository;

    private final ReviewRepository reviewRepository;

    @Override
    public Long saveReview(SaveReviewForm saveReviewForm) {

        Toilet toilet = toiletRepository.findById(saveReviewForm.getToiletId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_TOILET_ERROR_MSG));

        Review review = Review.builder()
                .writer(saveReviewForm.getWriter())
                .content(saveReviewForm.getContent())
                .starRating(saveReviewForm.getStarRating())
                .toilet(toilet)
                .build();

        return reviewRepository.save(review).getId();
    }

    @Override
    public Page<ReviewRes> findByReviewToToilet(Long toiletId, Pageable pageable) {

        Toilet toilet = toiletRepository.findById(toiletId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_TOILET_ERROR_MSG));

        Page<Review> reviews = reviewRepository.findByToilet(toilet, pageable);

        return ReviewRes.reviewToReviewRes(reviews);
    }

    @Override
    public ReviewRes findByReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_REVIEW_ERROR_MSG));

        return ReviewRes.builder()
                .toiletId(review.getToilet().getId())
                .content(review.getContent())
                .writer(review.getWriter())
                .starRating(review.getStarRating())
                .regDate(review.getRegDate().toString())
                .build();
    }

    @Override
    public Long modifyReview(ModifyReviewForm modifyReviewForm) {

        Review review = reviewRepository.findById(modifyReviewForm.getId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_REVIEW_ERROR_MSG));

        review.modify(modifyReviewForm);

        return reviewRepository.save(review).getId();
    }

    @Override
    public Long deleteReview(Long id) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_REVIEW_ERROR_MSG));

        reviewRepository.delete(review);

        return id;
    }
}
