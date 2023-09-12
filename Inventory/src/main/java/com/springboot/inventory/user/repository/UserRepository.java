package com.springboot.inventory.user.repository;

import com.springboot.inventory.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 유저 레파지토리 커스텀
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByNickname(String nickname);


}
