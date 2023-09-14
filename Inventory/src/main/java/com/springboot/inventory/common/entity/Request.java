package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.RequestTypeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "request")
@Getter
@Setter
public class Request extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RequestTypeEnum requestType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplyId")
    private Supply supply;

    private String content;

    private Boolean accept;

    private String requestStatus;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", nullable = false)
    private User user;


}
