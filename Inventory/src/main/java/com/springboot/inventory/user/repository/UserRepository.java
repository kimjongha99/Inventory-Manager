package com.springboot.inventory.user.repository;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    Optional<User> deleteByUsername(String username);
}