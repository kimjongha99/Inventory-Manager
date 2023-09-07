package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.LargeCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String categoryName;

    @Enumerated(EnumType.STRING)
    private LargeCategory largeCategory;

    private Boolean deleted;

    @Builder
    public Category(String categoryName, LargeCategory largeCategory, Boolean deleted) {
        this.categoryName = categoryName;
        this.largeCategory = largeCategory;
        this.deleted = deleted;
    }

    public void update(String categoryName, LargeCategory largeCategory) {
        this.categoryName = categoryName;
        this.largeCategory = largeCategory;
    }

    // 재등록 시 이름 변경
    public void reEnroll() {
        this.categoryName = this.categoryName + "(삭제됨#" + this.id + ")";
    }
}
