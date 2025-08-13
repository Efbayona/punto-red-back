package com.project.punto_red.recharge.service;

import com.project.punto_red.recharge.dto.RechargeHistoryResponse;
import com.project.punto_red.recharge.dto.RechargeRequest;
import com.project.punto_red.recharge.dto.RechargeResponse;
import org.springframework.data.domain.Page;

public interface RechargeService {

    RechargeResponse recharge(RechargeRequest request);

    Page<RechargeHistoryResponse> getRechargeRequests(int page);

}
