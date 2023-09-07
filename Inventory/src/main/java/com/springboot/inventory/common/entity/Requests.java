package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.AcceptResult;
import com.springboot.inventory.common.enums.RequestStatus;
import com.springboot.inventory.common.enums.RequestType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Requests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    // 요청서 타입
    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    // 요청 내용
    @Column(nullable = false)
    private String content;

    // Admin 메시지
    private String comment;

    // 승인 결과
    @Enumerated(EnumType.STRING)
    private AcceptResult acceptResult;

    // 처리 상태
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @BatchSize(size = 10)
    @OneToMany(mappedBy = "requests", cascade = CascadeType.ALL)
    private List<Image> imageList = new ArrayList<>();

    // 비품 요청을 할 때엔 Supply가 null
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplyId")
    private Supply supply;

    // 비품 요청의 경우 category를 받음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    
}
