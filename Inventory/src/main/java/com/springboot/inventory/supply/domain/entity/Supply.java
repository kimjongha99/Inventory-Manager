package com.springboot.inventory.supply.domain.entity;

import com.springboot.inventory.supply.domain.enums.SupplyStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE supply SET deleted = true WHERE supply_Id = ?")
public class Supply extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplyId; // 기본키

    @Column(nullable = false, unique = true)
    private String serialNum; //시리얼 번호

    private String modelContent; // 비품 내용 ?

    private int amount; // 비품 재고 수량

    private String image; // 비품 이미지

    @Column(nullable = false)
    private String modelName; // 비품 이름

    @Column(nullable = false)
    private boolean deleted; // 비품 삭제 여부

    @Enumerated(EnumType.STRING)
    private SupplyStatusEnum status; // 비품 상태


    @ManyToOne(fetch = FetchType.EAGER) // 지연로딩
    @JoinColumn(name = "userId")
    private User user; //사용자

    @ManyToOne(fetch = FetchType.EAGER) //LAZY 오류
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // 분류

    @Builder
    public Supply(String serialNum, String modelContent, int amount, String image, String modelName, boolean deleted, SupplyStatusEnum status, User user, Category category) {
        this.serialNum = serialNum;
        this.modelContent = modelContent;
        this.amount = amount;
        this.image = image;
        this.modelName = modelName;
        this.deleted = deleted;
        this.status = status;
        this.user = user;
        this.category = category;
    }




}
