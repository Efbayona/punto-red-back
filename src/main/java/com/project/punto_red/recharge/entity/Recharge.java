package com.project.punto_red.recharge.entity;

import com.project.punto_red.common.util.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "recharge", schema = "main")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Recharge extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "transactional_id", nullable = false, unique = true)
    private UUID transactionalID;

    @Column(name = "cell_phone", nullable = false, length = 20)
    private String cellPhone;

    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "supplier_id", nullable = false)
    private String supplierId;

    public Recharge() {
    }

    public Recharge(String message, UUID transactionalID, String cellPhone, Double value, String supplierId) {
        this.message = message;
        this.transactionalID = transactionalID;
        this.cellPhone = cellPhone;
        this.value = value;
        this.supplierId = supplierId;
    }

    public static Recharge create(String message, UUID transactionalID, String cellPhone, Double value, String supplierId) {
        return new Recharge(message, transactionalID, cellPhone, value, supplierId);
    }

}
