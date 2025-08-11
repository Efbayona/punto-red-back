package com.project.punto_red.recharge.service;

import com.project.punto_red.recharge.dto.RechargeHistoryResponse;
import com.project.punto_red.recharge.dto.RechargeRequest;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface RechargeService {

    HttpStatus recharge(RechargeRequest request);

    List<RechargeHistoryResponse> getRechargeRequests();

}
