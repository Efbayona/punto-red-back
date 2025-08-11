package com.project.punto_red.operators.controller;

import com.project.punto_red.operators.dto.OperatorResponse;
import com.project.punto_red.operators.service.OperatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/operator")
public class OperatorController {

    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @GetMapping("/")
    @Operation(description = "Get Operators")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "CANNOT GET OPERATORS")
    })
    public ResponseEntity<String> operators() {
        String operatorsJson = operatorService.getOperators();
        return ResponseEntity.ok(operatorsJson);
    }

}
