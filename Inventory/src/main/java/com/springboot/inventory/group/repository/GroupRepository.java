package com.springboot.inventory.group.repository;

import com.springboot.inventory.common.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,Long> {
}
