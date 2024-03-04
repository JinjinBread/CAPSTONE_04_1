package univcapstone.employmentsite.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.service.AuthService;


@Slf4j
@RestController
@RequiredArgsConstructor
public class BasicController {

    private final AuthService authService;

    @GetMapping("/home")
    public String home(Authentication authentication) {
        log.info("current user login_id = {}", authentication.getName());
        return authentication.getName();
    }

    @GetMapping("/")
    public String loginForm() {
        return "login form"; // 로그인 페이지
    }
}
