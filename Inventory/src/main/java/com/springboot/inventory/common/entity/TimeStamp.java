package com.springboot.inventory.common.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
// 캡슐화, 테이블과 직접 매핑 X, 상속받은 자식 클래스에 적용
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
/*
CreatedDate, LastModifiedDate 가 동작하기 위해서는
엔터티 이벤트 리스너를 등록해서
해당 엔터티(데이터)의 생성주기에 해당하는 이벤트기 발생할 때를 수신해주어야 한다.
*/
public class TimeStamp {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;


}
