package com.springboot.inventory.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String groupName;

    private Boolean deleted;

    @Builder
    public Team(String groupName) {
        this.groupName = groupName;
        this.deleted = false;
    }

    public void update(String groupName) {
        this.groupName = groupName;
    }
}
