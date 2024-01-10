package univcapstone.employmentsite.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class LoginController {

    //UserService

    @GetMapping("/login")
    public String loginForm() {
        return "login/loginForm"; // 로그인 페이지
    }
    @PostMapping("/login")
    public String login(
            @RequestParam("id") String id,
            @RequestParam("pw") String password) {
        //로그인에 대한 로직
        return "redirect:/"; // 로그인 성공 후 메인 페이지로 리다이렉트
    }

    @GetMapping("/logout")
    public String logout() {
        //로그아웃에 대한 로직
        return "redirect:/"; // 로그아웃 성공 후 메인 페이지로 리다이렉트
    }

}
