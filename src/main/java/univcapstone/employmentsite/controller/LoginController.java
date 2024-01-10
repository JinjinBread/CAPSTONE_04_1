package univcapstone.employmentsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import univcapstone.employmentsite.dto.UserDTO;

@Controller
@RequestMapping(value = "/jobhak.univ/login")
public class LoginController {
    @GetMapping()
        public String show(){
        return "login.html";
    }
    @PostMapping()
        public String login(UserDTO user){
            return "redirect:/jobhak.univ";
    }
}
