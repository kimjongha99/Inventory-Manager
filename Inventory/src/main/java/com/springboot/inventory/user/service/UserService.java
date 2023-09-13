package com.springboot.inventory.user.service;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.user.dto.UserDTO;
import com.springboot.inventory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(UserDTO userDTO) {
        // 1. dto -> entity 변환
        User user = User.toUser(userDTO);
        // 2. repository의 save 메서드 호출
        userRepository.save(user); // 반드시 save로 써야함!  기본값
    }

    public UserDTO login(UserDTO userDTO) {
        /*
            1. 회원이 입력한 이메일로 DB에서 조회를 함
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<User> byUserName = userRepository.findByUsername(userDTO.getUsername());
        if (byUserName.isPresent()) {
            // 조회 결과가 있다 (해당 이메일을 가진 회원 정보가 있다)
            User user = byUserName.get(); // optional로 감싸진 객체를 가져온다 ( 포장지를 벗기는 느낌)
            if (user.getPassword().equals(userDTO.getPassword())) {
                // 비밀번호 일치, String 값이라서 equals로 비교함
                // entity -> dto 변환 후 리턴
                UserDTO dto = UserDTO.toUserDto(user);
                return dto;
            } else {
                // 비밀번호 불일치
                return null;
            }
        } else {
            // 조회 결과가 없다
            return null;
        }
    }

    public List<UserDTO> findAll() {
        List<User> userList = userRepository.findAll(); // repository에 있는 기능 findall , 무조건 엔티티를 받아옴
        List<UserDTO> userDTOList = new ArrayList<>();
        // 엔티티가 여러개 담긴 리스트 -> DTO를 담는 리스트로 변환
        for (User user : userList) {
            userDTOList.add(UserDTO.toUserDto(user));
        }
        return userDTOList;
    }

    public UserDTO findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            return UserDTO.toUserDto(optionalUser.get()); //optional로 감싸있는 엔티티를 get하고 dto로 변환해서 리턴
        } else {
            return null;
        }
    }

    public UserDTO updateForm(String myUsername) {
        Optional<User> optionalUser = userRepository.findByUsername(myUsername);
        if(optionalUser.isPresent()) {
            return UserDTO.toUserDto(optionalUser.get());
        } else {
            return null;
        }
    }

    public void update(UserDTO userDTO) {
        userRepository.save(User.toUpdateUser(userDTO)); // 엔티티에 값이 있는 경우 저장이아닌 업데이트 수행
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}


