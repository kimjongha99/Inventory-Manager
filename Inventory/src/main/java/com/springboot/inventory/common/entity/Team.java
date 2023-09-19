package com.springboot.inventory.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE department SET deleted = true WHERE id = ?")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String teamName;

    private Boolean deleted;

    @Builder
    public Team(String teamName, Boolean deleted) {
        this.teamName = teamName;
        this.deleted = deleted;
    }
}
