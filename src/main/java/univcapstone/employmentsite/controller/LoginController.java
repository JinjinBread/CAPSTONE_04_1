package univcapstone.employmentsite.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.dto.TokenDto;
import univcapstone.employmentsite.dto.UserLoginDto;
import univcapstone.employmentsite.service.UserService;
import univcapstone.employmentsite.token.TokenProvider;

import java.io.IOException;

import static univcapstone.employmentsite.util.AuthConstants.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

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

//    @PostMapping("/")
    public ResponseEntity<TokenDto> login(@RequestBody @Validated UserLoginDto userLoginDto) {

        //보안 상 서버 단에서 아이디와 패스워드 유효성 검사 필요(UsernamePasswordAuthenticationFilter)
        
        //로그인에 대한 로직
        TokenDto tokenDto = userService.login(userLoginDto);

        return ResponseEntity.ok(tokenDto);

//        //로그인에 대한 로직
//        User loginUser = userService.login(userLoginDto);
//
//        log.info("로그인 유저 정보 = {}", userLoginDto);
//
////        HttpSession session = request.getSession();
////        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
//
//        DefaultResponse<User> defaultResponse = DefaultResponse.<User>builder()
//                .code(HttpStatus.OK.value())
//                .httpStatus(HttpStatus.OK)
//                .message("로그인 성공!")
//                .result(loginUser)
//                .build();
//
//        return ResponseEntity.ok()
//                .body(defaultResponse);

    }

}
