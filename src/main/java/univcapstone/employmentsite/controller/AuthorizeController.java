package univcapstone.employmentsite.controller;

import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.dto.UserDTO;

@RestController
@RequestMapping(value = "/jobhak.univ")
public class AuthorizeController {

    @GetMapping("/join")
    public String joinPage(){
        //회원가입 페이지
        return "";
    }
    @PostMapping("/join")
    public String join(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw,
            @RequestParam("name") String name,
            @RequestParam("nickname") String nickname,
            @RequestParam("email") String email) {
        //회원가입에 대한 로직
        UserDTO user=new UserDTO(id,pw,email,name,nickname);
        return "redirect:"; // 회원가입 성공 후 메인 페이지로 리다이렉트
    }

    @GetMapping("/login")
    public String loginPage(){
        //로그인 페이지
        return "";
    }
    @PostMapping("/login")
    public String login(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw ){
        //로그인에 대한 로직
        return "redirect:"; // 로그인 성공 후 메인 페이지로 리다이렉트
    }

    @RequestMapping(value = "/logout")
    @GetMapping
    public String logout() {
        //로그아웃에 대한 로직
        return "redirect:"; // 로그아웃 성공 후 메인 페이지로 리다이렉트
    }

    @RequestMapping(value = "/confirm/email")
    @PostMapping
    public String confirmEmail(@RequestParam("email") String email) {
        //이메일 인증에 대한 로직
        return "redirect:"; // 이메일 인중 성공 후 메인 페이지로 리다이렉트
    }

    @RequestMapping(value = "/verify/id")
    @PostMapping
    public String verifyID(@RequestParam("id") String id) {
        //아이디 중복 확인에 대한 로직
        return ""; //아이디 중복확인
    }
    @PostMapping("/find/id")
    public String findID(@RequestParam("name") String name,
                         @RequestParam("email") String email) {
        //아이디 찾기에 대한 로직
        return ""; //아이디 찾기 페이지
    }

    @GetMapping("/find/pw")
    public String findIdPage(){
        return "";
    }
    @PostMapping("/find/pw")
    public String findID(@RequestParam("id") String id,
                         @RequestParam("name") String name,
                         @RequestParam("email") String email) {
        //비밀번호 찾기에 대한 로직
        return "findPW.html"; //비밀번호 찾기 페이지
    }
}

