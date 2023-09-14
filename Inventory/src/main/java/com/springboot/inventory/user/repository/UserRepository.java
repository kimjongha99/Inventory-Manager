package com.springboot.inventory.user.repository;

//
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.user.repository.support.UserRepositoryCustom;

//
import org.springframework.data.jpa.repository.JpaRepository;

//
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByEmail(String email);

}
