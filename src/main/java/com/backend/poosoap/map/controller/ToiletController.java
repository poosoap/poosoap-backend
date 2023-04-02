package com.backend.poosoap.map.controller;

import com.backend.poosoap.common.utils.ApiUtils.ApiResult;
import com.backend.poosoap.map.dto.req.ToiletReq;
import com.backend.poosoap.map.service.ToiletService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.backend.poosoap.common.utils.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/toilet")
public class ToiletController {

    private final ToiletService toiletService;

    @PostMapping
    @Operation(summary = "화장실 등록", description = "화장실을 등록하는 api")
    public ApiResult<Long> createdToilet(@RequestBody ToiletReq req) {
        return success(
                toiletService.saveToilet(req)
        );
    }
}
