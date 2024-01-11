package univcapstone.employmentsite.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "Hello home";
    }
}
