package com.project.punto_red.recharge.controller;

import com.project.punto_red.recharge.dto.RechargeRequest;
import com.project.punto_red.recharge.service.RechargeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<HttpStatus> recharge(@Valid @RequestBody RechargeRequest request) {
        return new ResponseEntity<>(rechargeService.recharge(request), HttpStatus.OK);
    }

}

