package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.SupplyStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplyId;

    @Column(nullable = false, unique = true)
    private String serialNum;

    private String modelContent;

    private int amount;

    private String image;

    @Column(nullable = false)
    private String modelName;

    @Column(nullable = false)
    private boolean deleted;

    private SupplyStatusEnum status;

    @Builder
    public Supply( String serialNum, String modelContent, int amount, String image, String modelName, boolean deleted, SupplyStatusEnum status) {
        this.serialNum = serialNum;
        this.modelContent = modelContent;
        this.amount = amount;
        this.image = image;
        this.modelName = modelName;
        this.deleted = deleted;
        this.status = status;
    }
}
