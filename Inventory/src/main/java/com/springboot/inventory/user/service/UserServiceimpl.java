package com.springboot.inventory.user.service;

import com.springboot.inventory.common.entity.Group;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import com.springboot.inventory.group.repository.GroupRepository;
import com.springboot.inventory.user.dto.UserAllDto;
import com.springboot.inventory.user.dto.UserDto;
import com.springboot.inventory.user.dto.UserUpdateDto;
import com.springboot.inventory.user.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // final을 사용할 때 쓰는 생성자 에너테이션
public class UserServiceimpl implements UserService {

    private final UserRepository userRepository;

    public User getUser(String username) {
        try {
            Optional<User> byUsername = userRepository.findByUsername(username);

            if (byUsername.isPresent()) {
                User user = byUsername.get();
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            // 예외 처리 또는 로깅을 수행할 수 있습니다.
            e.printStackTrace(); // 예외 정보를 출력
            return null;
        }
    }

    public void signUp(UserDto userDto) { // 회원가입

        // 1. dto -> entity 변환
        User user = User.toEntity(userDto);

        // 2. repository save 메소드 호출
        userRepository.save(user);

    }

    public UserDto login(UserDto userDto) { // 로그인
        User user = getUser(userDto.getUsername());

        // 1. 회원이 입력한 이메일로 DB에서 조회를 함
        // 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는 지 확인
//        Optional<User> byUsername =
//                userRepository.findByUsername(userDto.getUsername());
//        if(byUsername.isPresent()){
        // 조회 결과가 있다. (해당 이메일을 가진 회원 정보가 있다.)
//            User user = byUsername.get();
        if (user.getPassword().equals(userDto.getPassword())) {
            // 비밀번호 일치
            // entity -> dto  변환 후 리턴
            UserDto dto = UserDto.toDto(user);
            return dto;
        } else {
            // 비밀번호 불일치(로그인 실패)
            return null;
        }
//        } else{
//            // 조회 결과가 없다.(해당 이메일을 가진 회원이 없다.)
//            return null;
//        }
    }

    @Override
    public List<UserDto> findAll() {    // 유저 전체 목록 보기
        List<User> userList = userRepository.findAll(); // 리스트 타입으로 레포지토리의 findAll()
        List<UserDto> userDtoList = new ArrayList<>(); // 새 ArrayList에다가 담아준다.

        for (User user : userList) { // 모든 유저 리스트들을 userDtoList의 담아준다.
            userDtoList.add(UserDto.toDto(user));
//            UserDto userDto = UserDto.toDto(user);
//            userDtoList.add(userDto);

        }
        return userDtoList; // 그리고 반환한다.
    }

    @Override
    public UserAllDto findUser(String username) {
        User user = getUser(username);

        if (user != null) {
            // 가져온 유저 정보를 UserAllDto로 변환하여 반환
            return UserAllDto.toDto(user);
        } else {
            return null; // username에 해당하는 유저가 없을 경우 null 반환
        }
    }

    public UserAllDto updateForm(String username) {
        User user = getUser(username);

        if (user != null) {
            // 가져온 유저 정보를 UserAllDto로 변환하여 반환
            return UserAllDto.toDto(user);
        } else {
            return UserAllDto.builder()
                    .username(username)
                    .name("Unknown")
                    .tel("N/A")
                    .alarm(false)
                    .group(null)
                    .build();
        }
    }
    @Transactional
    public void update(UserUpdateDto userDto) {
        // 1. 기존 회원 정보 가져오기
        User user = getUser(userDto.getUsername());

        if (user != null) {
            user.update(userDto);
            userRepository.save(user);
        }


    }
    @Transactional
    public void delete(String username){
        userRepository.deleteByUsername(username);
    }
}

