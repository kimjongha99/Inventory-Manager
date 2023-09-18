package com.springboot.inventory.user.repository;

import com.springboot.inventory.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
