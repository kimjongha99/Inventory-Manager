package com.springboot.inventory.common.entity;

import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
@MappedSuperclass // 상속한 데이터 연결
@EntityListeners(AuditingEntityListener.class) // 엔티티가 아님
@NoArgsConstructor
public class Timestamped {

    @CreatedDate // 엔티티에 생성 시간을 저장할 떄 쓰는 에너테이션
    private LocalDateTime created_at;

}
