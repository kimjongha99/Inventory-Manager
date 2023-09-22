package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.LargeCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE category SET deleted = true WHERE category_id = ?")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;


    private String categoryName;

    @Enumerated(EnumType.STRING)
    private LargeCategory largeCategory;

    private Boolean deleted= false;

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
