package com.project.punto_red.recharge.service;

import com.project.punto_red.recharge.dto.RechargeHistoryResponse;
import com.project.punto_red.recharge.dto.RechargeResponse;
import com.project.punto_red.recharge.service.domain.RechargeDomain;
import org.springframework.data.domain.Page;

public interface RechargeService {

    RechargeResponse recharge(RechargeDomain request);

    Page<RechargeHistoryResponse> getRechargeRequests(int page);

}
