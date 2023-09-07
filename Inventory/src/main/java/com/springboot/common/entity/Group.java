package com.springboot.common.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE department SET deleted = true WHERE id = ?")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String groupName;

    private Boolean deleted;


    @Builder
    public Group(String groupName) {
        this.groupName = groupName;
        this.deleted = false;
    }
}
