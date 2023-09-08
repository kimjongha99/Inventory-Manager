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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    // 비품 요청의 경우 category를 받음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    // 비품 요청/반납 시 부서 저장
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private Group group;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminId")
    private User admin;

    public Requests(RequestType requestType, String content, AcceptResult acceptResult, RequestStatus requestStatus, List<Image> imageList, Supply supply, User user, Category category, Group group, User admin) {
        this.requestType = requestType;
        this.content = content;
        this.acceptResult = acceptResult;
        this.requestStatus = requestStatus;
        this.imageList = imageList;
        this.supply = supply;
        this.user = user;
        this.category = category;
        this.group = group;
        this.admin = admin;
    }

    // 요청 처리
    public void processingRequest(AcceptResult acceptResult, String comment, Supply supply, User admin) {
        // 처리중 상태 처리
        if (this.requestType.equals(RequestType.REPAIR) && acceptResult.equals(AcceptResult.ACCEPT)
        && this.requestStatus.equals(RequestStatus.UNPROCESSED)) {
            this.requestStatus = RequestStatus.PROCESSING;
            this.admin = admin;
            return;
        }

        // 비품 요청 시의 supply 기록
        if(this.requestType.equals(RequestType.SUPPLY) && acceptResult.equals(AcceptResult.ACCEPT)) {
            this.supply = supply;
        }

        this.acceptResult = acceptResult;
        this.requestStatus = RequestStatus.PROCESSED;
        this.comment = comment;
        this.admin = admin;
    }

    public void update(Requests requests) {
        this.content = requests.getContent();
    }

}
