package com.springboot.inventory.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_group")
@NoArgsConstructor
@Getter
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(nullable = false, unique = true)
    private String groupName;

    private Boolean deleted;

    @Builder
    public Group (String group_name) {
        this.groupName = group_name;
        this.deleted = false;
    }


}
