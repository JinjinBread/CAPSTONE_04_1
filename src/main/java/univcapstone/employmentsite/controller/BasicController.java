package univcapstone.employmentsite.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.service.AuthService;


@Slf4j
@RestController
@RequiredArgsConstructor
public class BasicController {

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
