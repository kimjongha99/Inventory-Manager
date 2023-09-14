package com.springboot.inventory.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Table(name = "`group`")
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

    @Builder
    public Group(String group_name){
        this.group_name = group_name;
        this.deleted = false;
    }
}
