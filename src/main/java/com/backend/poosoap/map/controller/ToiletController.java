package com.backend.poosoap.map.controller;

import com.backend.poosoap.common.utils.ApiUtils.ApiResult;
import com.backend.poosoap.map.dto.req.Location;
import com.backend.poosoap.map.dto.req.ModifyToiletForm;
import com.backend.poosoap.map.dto.req.SaveToiletForm;
import com.backend.poosoap.map.dto.res.ToiletsRes;
import com.backend.poosoap.map.service.ToiletService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.backend.poosoap.common.utils.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/toilet")
public class ToiletController {

    private final ToiletService toiletService;

    @PostMapping
    @Operation(summary = "화장실 등록", description = "화장실을 등록하는 api")
    public ApiResult<Long> createdToilet(@RequestBody SaveToiletForm req) {
        return success(
                toiletService.saveToilet(req)
        );
    }

    @GetMapping("/{latitude}/{longitude}")
    @Operation(summary = "화장실 찾기", description = "반경 1km 화장실을 찾는 api")
    public ApiResult<ToiletsRes> findByToiletInOneDistance(@PathVariable String latitude, @PathVariable String longitude) {
        return success(
                toiletService.findByToilet(new Location(latitude, longitude))
        );
    }

    @PatchMapping
    @Operation(summary = "화장실 수정", description = "화장실을 수정하는 api")
    public ApiResult<Long> modifyToilet(@RequestBody @Valid ModifyToiletForm modifyToiletForm) {
        return success(
                toiletService.modifyToilet(modifyToiletForm)
        );
    }

    @DeleteMapping("/{toiletId}")
    @Operation(summary = "화장실 삭제", description = "화장실을 삭제하는 api")
    public ApiResult<Long> deleteToilet(@PathVariable Long toiletId) {
        return success(
                toiletService.deleteToilet(toiletId)
        );
    }
}
