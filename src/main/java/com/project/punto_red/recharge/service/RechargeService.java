package com.project.punto_red.recharge.service;

import com.project.punto_red.recharge.dto.RechargeRequest;
import org.springframework.http.HttpStatus;

public interface RechargeService {

    HttpStatus recharge(RechargeRequest request);
}
