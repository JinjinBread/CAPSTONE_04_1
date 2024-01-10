package univcapstone.employmentsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import univcapstone.employmentsite.dto.UserDTO;

@Controller
@RequestMapping(value = "/jobhak.univ")
public class HomeController {
    @RequestMapping(method = RequestMethod.GET)
    public String home(){
        return "index.html";
    }
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String joinForm() {
        return "join.html"; // 회원가입 폼을 보여주는 뷰 이름으로 변경
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String join(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw,
            @RequestParam("name") String name,
            @RequestParam("nickname") String nickname,
            @RequestParam("email") String email) {
        //회원가입에 대한 로직
        UserDTO user=new UserDTO(id,pw,email,name,nickname);
        return "redirect:/jobhak.univ"; // 회원가입 성공 후 메인 페이지로 리다이렉트
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm() {
        return "login.html"; // 로그인 페이지
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw ){
        //로그인에 대한 로직
        return "redirect:/jobhak.univ"; // 로그인 성공 후 메인 페이지로 리다이렉트
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        //로그아웃에 대한 로직
        return "redirect:/jobhak.univ"; // 로그아웃 성공 후 메인 페이지로 리다이렉트
    }

    @RequestMapping(value = "/confirm/email", method = RequestMethod.POST)
    public String confirmEmail(@RequestParam("email") String email) {
        //이메일 인증에 대한 로직
        return "redirect:/jobhak.univ"; // 이메일 인중 성공 후 메인 페이지로 리다이렉트
    }

    @RequestMapping(value = "/verify/id", method = RequestMethod.POST)
    public String verifyID(@RequestParam("id") String id) {
        //아이디 중복 확인에 대한 로직
        return ""; //아이디 중복확인
    }

    @RequestMapping(value = "/find/id", method = RequestMethod.GET)
    public String findID() {
        //아이디 찾기 페이지
        return "findID.html"; //아이디 찾기 페이지
    }
    @RequestMapping(value = "/find/id", method = RequestMethod.POST)
    public String findID(@RequestParam("name") String name,
                         @RequestParam("email") String email) {
        //아이디 찾기에 대한 로직
        return "findID.html"; //아이디 찾기 페이지
    }

    @RequestMapping(value = "/find/pw", method = RequestMethod.POST)
    public String findID(@RequestParam("id") String id,
                         @RequestParam("name") String name,
                         @RequestParam("email") String email) {
        //비밀번호 찾기에 대한 로직
        return "findPW.html"; //비밀번호 찾기 페이지
    }
}
