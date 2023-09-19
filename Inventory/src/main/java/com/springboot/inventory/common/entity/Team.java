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
    private Long team_id;

    @Column(nullable = false, unique = true)
    private String teamName;

    private Boolean deleted;

    @Builder
    public Team(String teamName) {
        this.teamName = teamName;
        this.deleted = false;
    }

    public void update(String teamName) {
        this.teamName = teamName;
    }
}
