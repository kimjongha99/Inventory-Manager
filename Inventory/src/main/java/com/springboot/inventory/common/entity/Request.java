package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.RequestTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "request")
@Getter
@Setter
public class Request extends Timestamped implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RequestTypeEnum requestType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplyId", referencedColumnName = "supplyId")
    private Supply supply;

    private String content;

    private Boolean accept;

    private Boolean returnAvailable;

    @OneToOne
    @JoinColumn(name = "previous_request", referencedColumnName = "requestId")
    private Request request;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false)
    private User user;


}
