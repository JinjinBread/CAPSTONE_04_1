package univcapstone.employmentsite.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.UserLoginDto;
import univcapstone.employmentsite.ex.custom.UserAuthenticationException;
import univcapstone.employmentsite.service.UserService;
import univcapstone.employmentsite.util.SessionConst;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;

import java.io.IOException;

@Slf4j
@RestController
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String loginForm() {
        return "login form"; // 로그인 페이지
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //로그아웃에 대한 로직
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        log.info("로그아웃");

        return "logout"; // 로그아웃 성공 후 메인 페이지로 리다이렉트
    }

    @PostMapping("/")
    public ResponseEntity<? extends BasicResponse> login(@RequestBody @Validated UserLoginDto userDto, HttpServletRequest request) {

        //로그인에 대한 로직
        User loginUser = userService.login(userDto.getLoginId(), userDto.getPassword());

        log.info("로그인 유저 정보 = {}", userDto);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);

        DefaultResponse<User> defaultResponse = DefaultResponse.<User>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("로그인 성공!")
                .result(loginUser)
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);

    }

}
