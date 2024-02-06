package univcapstone.employmentsite.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.util.SessionConst;

@Slf4j
@RestController
public class HomeController {

    @GetMapping("/home")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user) {
        if (user == null)
            return "Hello home";
        return user.getNickname();
    }
}
