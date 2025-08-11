package com.project.punto_red.recharge.service;

import com.project.punto_red.recharge.dto.RechargeHistoryResponse;
import com.project.punto_red.recharge.dto.RechargeRequest;
import com.project.punto_red.recharge.dto.RechargeResponse;

import java.util.List;

public interface RechargeService {

    RechargeResponse recharge(RechargeRequest request);

    List<RechargeHistoryResponse> getRechargeRequests();

}
