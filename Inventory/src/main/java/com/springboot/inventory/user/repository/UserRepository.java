package com.springboot.inventory.user.repository;

import com.springboot.common.entity.User;
import com.springboot.common.enums.UserRoleEnum;
import com.springboot.common.queryDSL.user.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    List<User> findByRoleAndAlarmAndDeletedFalse(UserRoleEnum admin, boolean b);

    List<User> findByDeletedFalseAndDepartmentNotNull();

    Optional<User> findByIdAndDeletedFalse(Long userId);

    List<User> findByDepartmentAndDeletedFalse(Department department);

    boolean existsByDepartment_IdAndDeletedFalse(Long id);

    Optional<User> findByEmpNameAndDepartment_DeptNameAndDeletedFalse(String empName, String deptName);
}
