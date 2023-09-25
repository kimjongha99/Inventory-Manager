package com.springboot.inventory.user.repository;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import com.springboot.inventory.user.dto.UserInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(Long id);
    boolean existsByEmail (String email);
    User getByEmail(String email);
    void deleteByEmail(String email);

    List<User> findByRoles(UserRoleEnum role);

    List<User> findByDeletedFalse();


    Optional<User> findById(Long userId);

}
