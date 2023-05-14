package com.backend.poosoap.map.controller;

import com.backend.poosoap.common.utils.ApiUtils.ApiResult;
import com.backend.poosoap.map.dto.req.ModifyReviewForm;
import com.backend.poosoap.map.dto.req.SaveReviewForm;
import com.backend.poosoap.map.dto.res.ReviewRes;
import com.backend.poosoap.map.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import static com.backend.poosoap.common.utils.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/toilet/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "리뷰 등록", description = "리뷰를 등록하는 api")
    public ApiResult<Long> createdReview(@RequestBody SaveReviewForm req) {
        return success(
                reviewService.saveReview(req)
        );
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "리뷰 글 상세보기", description = "리뷰 글 상세 조회하는 api")
    public ApiResult<ReviewRes> findByDoodle(@PathVariable Long reviewId) {
        return success(
                reviewService.findByReview(reviewId)
        );
    }

    @GetMapping
    @Operation(summary = "화장실 전체 리뷰 조회", description = "화장실의 전체 리뷰를 조회하는 api")
    public ApiResult<Page<ReviewRes>> findByReviewToToilet(
            @RequestParam("toiletId") Long toiletId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String sort) {

        Sort pageableSort = Sort.by(Sort.Direction.DESC, sort);
        Pageable pageable = PageRequest.of(page, size, pageableSort);

        return success(
                reviewService.findByReviewToToilet(toiletId, pageable)
        );
    }

    @PatchMapping
    @Operation(summary = "리뷰 수정", description = "리뷰를 수정하는 api")
    public ApiResult<Long> modifyReview(@RequestBody @Valid ModifyReviewForm modifyReviewForm) {
        return success(
                reviewService.modifyReview(modifyReviewForm)
        );
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제하는 api")
    public ApiResult<Long> deleteDoodle(@PathVariable Long reviewId) {
        return success(
                reviewService.deleteReview(reviewId)
        );
    }

}
