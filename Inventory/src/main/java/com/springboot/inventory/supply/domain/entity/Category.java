package com.springboot.inventory.supply.domain.entity;

import com.springboot.inventory.supply.domain.enums.LargeCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE category SET deleted = true WHERE id = ?")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String categoryName;

    @Enumerated(EnumType.STRING)
    private LargeCategory largeCategory;

    private Boolean deleted;
}
