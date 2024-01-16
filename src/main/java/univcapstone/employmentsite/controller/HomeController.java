package univcapstone.employmentsite.controller;

<<<<<<< HEAD
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/jobhak.univ")
public class HomeController {
    @GetMapping("")
    public String home(){
        return "index.html";
=======
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.util.SessionConst;

@Slf4j
@RestController
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user) {
        if (user == null)
            return "Hello home";
        return "Hello login home";
>>>>>>> 8c17459dc5b763643558a8152485d0f74f3e0d54
    }
}
