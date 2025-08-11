package com.project.punto_red.recharge.repository;

import com.project.punto_red.recharge.entity.Recharge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RechargeRepository extends JpaRepository<Recharge, UUID> {

}
