package com.springboot.inventory.user.repository.support;


import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    public String temp() {
        return "Temp";
    };

}
