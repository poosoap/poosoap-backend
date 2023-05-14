package com.backend.poosoap.map.controller;

import com.backend.poosoap.common.utils.ApiUtils.ApiResult;
import com.backend.poosoap.map.dto.req.Location;
import com.backend.poosoap.map.dto.req.ModifyDoodleForm;
import com.backend.poosoap.map.dto.req.ReactionForm;
import com.backend.poosoap.map.dto.req.SaveDoodlesForm;
import com.backend.poosoap.map.dto.res.DoodlesRes;
import com.backend.poosoap.map.dto.res.FindDoodles;
import com.backend.poosoap.map.dto.res.ReactionRes;
import com.backend.poosoap.map.service.DoodleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.backend.poosoap.common.utils.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/toilet/doodle")
public class DoodleController {

    private final DoodleService doodleService;

    @PostMapping
    @Operation(summary = "낙서장 등록", description = "낙서장을 등록하는 api")
    public ApiResult<Long> createdDoodle(@RequestBody SaveDoodlesForm req) {
        return success(
                doodleService.saveDoodles(req)
        );
    }

    @GetMapping("/{toiletId}")
    @Operation(summary = "낙서장 글 상세보기", description = "반경 1km 낙서장을 찾는 api")
    public ApiResult<FindDoodles> findByDoodle(@PathVariable Long toiletId) {
        return success(
                doodleService.findByDoodle(toiletId)
        );
    }

    @GetMapping
    @Operation(summary = "화장실에 낙서글이 얼마나 있는지 찾기", description = "반경 1km 낙서장을 찾는 api")
    public ApiResult<DoodlesRes> findByDoodleInOneDistance(
            @RequestParam("radius") String radius,
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude) {
        return success(
                doodleService.findByDoodles(new Location(radius, latitude, longitude))
        );
    }

    @PatchMapping
    @Operation(summary = "낙서장 수정", description = "낙서장을 수정하는 api")
    public ApiResult<Long> modifyDoodle(@RequestBody @Valid ModifyDoodleForm modifyDoodleForm) {
        return success(
                doodleService.modifyDoodles(modifyDoodleForm)
        );
    }

    @DeleteMapping("/{doodleId}")
    @Operation(summary = "낙서장 삭제", description = "낙서장을 삭제하는 api")
    public ApiResult<Long> deleteDoodle(@PathVariable Long doodleId) {
        return success(
                doodleService.deleteDoodles(doodleId)
        );
    }

    @PostMapping("/reactions")
    @Operation(summary = "낙서장 좋아요, 공감 기능", description = "낙서장 좋아요, 공감 등록하는 api")
    public ApiResult<ReactionRes> updateLikeLove(@RequestBody ReactionForm req) {
        return success(
                doodleService.likeLoveCount(req)
        );
    }
}
