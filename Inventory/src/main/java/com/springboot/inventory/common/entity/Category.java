package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.LargeCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE category SET deleted = true WHERE id = ?")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(unique = true)
    private String categoryName;

    @Enumerated(EnumType.STRING)
    private LargeCategory largeCategory;

    private Boolean deleted;

    @OneToMany(mappedBy = "category")
    private List<Supply> supplies = new ArrayList<>();

    @Builder
    public Category(String categoryName, LargeCategory largeCategory, Boolean deleted) {
        this.categoryName = categoryName;
        this.largeCategory = largeCategory;
        this.deleted = deleted;
    }

    @Builder
    public Category(Long categoryId, String categoryName, LargeCategory largeCategory) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.largeCategory = largeCategory;
    }
}
