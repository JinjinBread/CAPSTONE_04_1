package univcapstone.employmentsite.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import univcapstone.employmentsite.dto.UserLoginForm;

@Slf4j
@RestController
public class LoginController {

    //UserService

    @GetMapping("/login")
    public String loginForm() {
        return "login form"; // 로그인 페이지
    }
    @PostMapping("/login")
    public String login(UserLoginForm form) {
        //로그인에 대한 로직

        return "ok"; // 로그인 성공 후 메인 페이지로 리다이렉트
    }

    @GetMapping("/logout")
    public String logout() {
        //로그아웃에 대한 로직
        return "redirect:/"; // 로그아웃 성공 후 메인 페이지로 리다이렉트
    }

}
