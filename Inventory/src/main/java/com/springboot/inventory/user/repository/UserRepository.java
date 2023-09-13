package com.springboot.inventory.user.repository;

import com.springboot.inventory.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // null 방지를 위함

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //엔티티이름 , 기본키타입
    // Repository는 엔티티 객체를 받아야한다.

    // 이메일로 회원 정보 조회 (select * from member_table where username=?)
    Optional<User> findByUsername(String username); //엔티티 객체로 받아서 username을 넘겨준다.
}
