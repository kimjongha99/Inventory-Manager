package com.springboot.inventory.user.repository;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    Optional<User> findByIdAndDeletedFalse(Long userId);

    List<User> findByRoleAndDeletedFalse(UserRoleEnum admin);

    List<User> findByDeletedFalseAndGroupNotNull();

    Optional<User> findByNameAndGroup_GroupNameAndDeletedFalse(String name, String groupName);

}
