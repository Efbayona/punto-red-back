package com.project.punto_red.recharge.controller;

import com.project.punto_red.recharge.dto.RechargeHistoryResponse;
import com.project.punto_red.recharge.dto.RechargeRequest;
import com.project.punto_red.recharge.dto.RechargeResponse;
import com.project.punto_red.recharge.service.RechargeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recharge")
public class RechargeController {

    private final RechargeService rechargeService;

    public RechargeController(RechargeService rechargeService) {
        this.rechargeService = rechargeService;
    }

    @PostMapping("/")
    @Operation(description = "Recharge Movile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "CANT NOT RECHARGE")
    })
    public ResponseEntity<RechargeResponse> recharge(@Valid @RequestBody RechargeRequest request) {
        return new ResponseEntity<>(rechargeService.recharge(request), HttpStatus.OK);
    }

    @GetMapping("/history")
    @Operation(description = "History recharge")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "CANT NOT GET RECHARGES")
    })
    public ResponseEntity<Page<RechargeHistoryResponse>> historyRecharge(@RequestParam(defaultValue = "0") int page) {
        return new ResponseEntity<>(rechargeService.getRechargeRequests(page), HttpStatus.OK);
    }


}

