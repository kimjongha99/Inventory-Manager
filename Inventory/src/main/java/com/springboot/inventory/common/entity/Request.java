package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.RequestTypeEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "request")
@Getter
@Setter
public class Request extends TimeStamp implements Serializable {

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

    @Column
    private Boolean returnAvailable = true;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false)
    private User user;


}
