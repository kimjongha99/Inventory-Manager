package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.SupplyStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Supply extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplyId;

    @Column(nullable = false, unique = true)
    private String serialNum;

    @Column(nullable = false)
    private String modelName;

    // 비품 상태
    @Enumerated(EnumType.STRING)
    private SupplyStatusEnum status;


    // 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Boolean deleted;


    @Builder
    public Supply(String serialNum, String modelName, SupplyStatusEnum status, User user,
                  Category category, Group group, Boolean deleted) {
        this.serialNum = serialNum;
        this.modelName = modelName;
        this.group = group;
        this.status = status;
        this.user = user;
        this.category = category;
        this.deleted = deleted;
    }

    // 등록 시 등록일자를 기입한 경우
    public void changeCreatedAt(LocalDateTime createdAt) {
        super.insertCreatedAt(createdAt);
    }

    // 비품 재등록(SoftDelete 된 비품이 재등록 됬을 때 처리.)
    public void reEnroll() {
        this.serialNum = "재등록된 비품#" + supplyId;
        this.modelName = "재등록된 비품#" + supplyId;
    }


    // 비품 배정.
    public void allocateSupply(Requests request, Group group) {
        this.group = group; // group 직접 연결
        this.status = this.status.equals(SupplyStatusEnum.REPAIRING) ? SupplyStatusEnum.REPAIRING : SupplyStatusEnum.USING;
    }

    // 비품 수리
    public void repairingSupply() {
        this.status = SupplyStatusEnum.REPAIRING;
    }

    // 비품 반납
    public void returnSupply() {
        this.user = null;
        this.group = null;
        this.status = this.status.equals(SupplyStatusEnum.REPAIRING) ? SupplyStatusEnum.REPAIRING : SupplyStatusEnum.STOCK;
    }
}
