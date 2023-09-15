package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.RequestTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "supply")
@Getter
@Setter
public class Supply extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplyId;

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "categoryId")
    private Category category;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private String modelName;

    @Enumerated(value = EnumType.STRING)
    private RequestTypeEnum state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName = "email")
    private User user;

}
