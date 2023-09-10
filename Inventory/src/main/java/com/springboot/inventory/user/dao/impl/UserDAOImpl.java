package com.springboot.inventory.user.dao.impl;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.user.dao.UserDAO;
import com.springboot.inventory.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDAOImpl implements UserDAO {

    private final UserRepository userRepository;

    @Autowired
    public UserDAOImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User insertUserData(User user) {
        return userRepository.save(user);
    }

}
