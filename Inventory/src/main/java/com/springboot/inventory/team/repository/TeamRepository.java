package com.springboot.inventory.team.repository;

import com.springboot.inventory.common.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {
    Optional<Team> findByIdAndDeletedFalse(Long teamId);
}
