package univcapstone.employmentsite.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/jobhak.univ")
public class HomeController {
    @GetMapping("")
    public String home(){
        return "index.html";
    }
}
