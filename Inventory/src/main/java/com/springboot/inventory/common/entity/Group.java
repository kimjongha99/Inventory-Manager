package com.springboot.inventory.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // primary key
    private Long group_id; // 그룹 번호

    @Column(unique = true)
    private String group_name; // 그룹 이름

    private Boolean deleted; // 삭제 여부
}
