package com.springboot.inventory.user.service;

import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import com.springboot.inventory.common.jwt.JwtTokenProvider;
import com.springboot.inventory.user.dto.SignInRequestDTO;
import com.springboot.inventory.user.dto.SignInResponseDTO;
import com.springboot.inventory.user.dto.UserDTO;
import com.springboot.inventory.user.dto.UserResponseDTO;
import com.springboot.inventory.user.repository.UserRepository;
import com.springboot.inventory.user.service.impl.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public ResponseDTO<UserResponseDTO> registerUser(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRoleEnum.USER);

        userRepository.save(user);

        return new ResponseDTO<>(true, null);
    }

    @Override
    public ResponseDTO<String> loginUser(SignInRequestDTO signInRequestDTO) {

        Optional<User> searchedUser = userRepository.findByEmail(signInRequestDTO.getEmail());

        if(searchedUser.isEmpty()) {
            System.out.println("등록되지 않은 이메일입니다.");
            return new ResponseDTO<>(false, null);
        }

        User user = searchedUser.get();

        if (passwordEncoder.matches(signInRequestDTO.getPassword(), user.getPassword())) {

            String role = user.getRole().toString();
            String email = user.getEmail();

            String token = jwtTokenProvider.genToken(email, role);

            return new ResponseDTO<>(true, token);
        }

        System.out.println("비밀번호가 일치하지 않습니다.");

        return new ResponseDTO<>(false, null);
    }
}
