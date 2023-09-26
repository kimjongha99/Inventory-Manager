package com.springboot.inventory.user.controller;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import com.springboot.inventory.common.security.UserDetailsImpl;
import com.springboot.inventory.user.dto.*;
import com.springboot.inventory.user.service.UserService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user/")
public class UserRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/custom-login")
    @ResponseBody
    public ResponseEntity<SignInResultDto> signIn(@RequestBody SigninRequestDTO signinRequestDTO, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        LOGGER.info("[UserRestController - signIn]");

        String email = signinRequestDTO.getUsername();

        String password = signinRequestDTO.getPassword();

        SignInResultDto signInResultDto = userService.signIn(email, password, request, response);

        if (signInResultDto.isSuccess()) {
            return ResponseEntity.ok(signInResultDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(signInResultDto);
        }
    }

    @GetMapping("/role")
    public ResponseEntity<String> getUserRole(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String email = userDetails.getUsername();
        User user = userService.getUser(email);
        UserRoleEnum role = user.getRoles();

        if (role == UserRoleEnum.ADMIN) {
            return ResponseEntity.ok("/AdminPage");
        } else if (role == UserRoleEnum.MANAGER) {
            return ResponseEntity.ok("/managerdashboard");
        } else {
            return ResponseEntity.ok("/dashboard");  // Change to the desired URL
        }
    }


    @PostMapping(value = "/sign-up")
    public SignUpResultDto signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto,
                                  HttpServletResponse response) {
        try {
            // 유효성 검사 통과 시 처리
            signUpRequestDto.setTeam("미정");

            LOGGER.info("[UserRestController - signUp]");
            SignUpResultDto signUpResultDto = userService.signUp(signUpRequestDto);
            LOGGER.info("[UserRestController - signUp 완료.]");

            // 회원가입이 성공한 경우, 메인 페이지로 리다이렉트
            if (signUpResultDto.isSuccess()) {
                try {
                    response.sendRedirect("/index");
                } catch (IOException e) {
                    // 리다이렉션 실패 시 처리
                    e.printStackTrace();
                }
            }
            return signUpResultDto;
        } catch (Exception e) {
            throw new YourCustomValidationException("유효성 검사 실패");
        }
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class YourCustomValidationException extends RuntimeException {
        public YourCustomValidationException(String message) {
            super(message);
        }
    }
    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetailsImpl userDetails,

                                       HttpServletRequest request,
                                       HttpServletResponse response) throws IOException, URISyntaxException {
        userService.logOut(userDetails.getUsername(), request, response);
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(new URI("/index"))
                .build();
    }


    // 회원 조회
    @GetMapping(value = "/MyPage")
    public ResponseEntity<Map<String, String>> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userEmail = userDetails.getUsername();
        Optional<User> user = userService.findByEmail(userEmail);

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("email", user.get().getEmail());
        userInfo.put("username", user.get().getUsername());
        userInfo.put("tel", user.get().getTel());

        if (user.isPresent()) {
            return ResponseEntity.ok(userInfo);
        } else {
            // 사용자 정보가 없는 경우
            return ResponseEntity.notFound().build();
        }
    }

    // 모든회원
    @GetMapping("/allUserlist")
    public ResponseEntity<List<UserInfoDto>> findAllUser() {
        List<UserInfoDto> userList = userService.findAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    // 팀 수정하기
    @PostMapping("/updateteam")
    public ResponseEntity<String> updateTeam(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        String team = requestData.get("team");
        
        userService.updateTeam(email, team);
        return ResponseEntity.status(HttpStatus.OK).body("팀이 업데이트되었습니다.");
    }

    // 모든 회원 조회 (ADMIN용)
    @GetMapping("/allUserListForAdmin")
    public ResponseEntity<List<UserInfoDto>> findAllUserForAdmin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userEmail = userDetails.getUsername();
        List<UserInfoDto> userList = userService.findAllUserForAdmin(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }
    
    // 로그인 한 계정의 권한 찾기
    @GetMapping("/get-role")
    public ResponseEntity<UserRoleEnum> getRole(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String email = userDetails.getUsername();
        System.out.println("RestController: "+ email);
        User user = userService.getUser(email);
        return ResponseEntity.ok().body(user.getRoles());
    }

    // 이메일 중복확인
    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam("email") String email) {
        boolean isEmailDuplicate = userService.doublecheck(email);

        if (isEmailDuplicate) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 이메일입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 이메일입니다.");
        }
    }
    // 권한 부여
    @PutMapping("/roles/{email}")
    public ResponseEntity<String> grantRole(@PathVariable String email,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.grantRole(email, userDetails.getUser().getRoles());
    }

    // 회원 삭제
    @DeleteMapping("/MyPage/delete")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             HttpServletRequest request,
                                             HttpServletResponse response) throws URISyntaxException {
        String userEmail = userDetails.getUsername();

        userService.deleteUser(userEmail, request, response);

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(new URI("/index"))
                .build();
    }

    // 비밀번호 수정
    @PutMapping("/MyPage/changePassword")
    public ResponseEntity<String> changepassword(@RequestBody ChangePasswordDto changePasswordDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.changePassword(changePasswordDto, userDetails.getUser());
    }

    // 회원 정보 수정
    @PutMapping("/MyPage/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserDto updateUserDto,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.updateUser(updateUserDto, userDetails.getUser());
    }
    // 회원 삭제 (매니저가 관리용)
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> delete(@PathVariable String email) throws URISyntaxException {

        userService.delete(email);

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(new URI("/ManagerPage"))
                .build();
    }

    @PostMapping("/checkPassword")
    public ResponseEntity<String> checkPassword(@RequestBody Map<String, String> password,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userEmail = userDetails.getUsername();

        return userService.checkPassword(userEmail, password.get("password"));
    }

}
