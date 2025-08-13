package com.project.punto_red.recharge.repository;

import com.project.punto_red.recharge.dto.RechargeHistoryResponse;
import com.project.punto_red.recharge.entity.Recharge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RechargeRepository extends JpaRepository<Recharge, UUID> {
    @Query("Select new com.project.punto_red.recharge.dto.RechargeHistoryResponse(r.message, r.cellPhone , r.value, r.transactionalID, r.supplierId, r.operator ,r.createdAt) from Recharge r order by r.createdAt desc ")
    Page<RechargeHistoryResponse> getRechargeRequests(Pageable pageable);

}
