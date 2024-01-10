package univcapstone.employmentsite.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class UserController {

    //UserService

    @GetMapping("/join")
    public String joinForm() {
        return "join.html"; // 회원가입 폼을 보여주는 뷰 이름으로 변경
    }

    @PostMapping("/join")
    public String join(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw,
            @RequestParam("nickname") String nickname,
            @RequestParam("email") String email) {
        //회원가입에 대한 로직
        return "redirect:/jobhak.univ"; // 회원가입 성공 후 메인 페이지로 리다이렉트
    }

    @PostMapping("/confirm/email")
    public String confirmEmail(@RequestParam("email") String email) {
        //이메일 인증에 대한 로직
        return "redirect:/jobhak.univ"; // 이메일 인중 성공 후 메인 페이지로 리다이렉트
    }

    @PostMapping("/verify/id")
    public String verifyID(@RequestParam("id") String id) {
        //아이디 중복 확인에 대한 로직
        return ""; //아이디 중복확인
    }

    @GetMapping("/find/id")
    public String findID() {
        //아이디 찾기 페이지
        return "findID.html"; //아이디 찾기 페이지
    }
    @PostMapping("/find/id")
    public String findID(@RequestParam("name") String name,
                         @RequestParam("email") String email) {
        //아이디 찾기에 대한 로직
        return "findID.html"; //아이디 찾기 페이지
    }

    @PostMapping("/find/pw")
    public String findID(@RequestParam("id") String id,
                         @RequestParam("name") String name,
                         @RequestParam("email") String email) {
        //비밀번호 찾기에 대한 로직
        return "findPW.html"; //비밀번호 찾기 페이지
    }
}
