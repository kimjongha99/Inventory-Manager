package com.springboot.inventory.common.userDetails;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    public UserDetailsServiceImpl (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> searched = userRepository.findByEmail(email);

        if(searched.isEmpty()) {
            throw new UsernameNotFoundException("Load By Username 오류");
        }

        User user = searched.get();

        return new CustomUserDetails(
                user, user.getEmail()
        );
    }
}
