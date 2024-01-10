package univcapstone.employmentsite.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jobhak.univ")
public class ResumeController {
    @GetMapping
    public String home(){
        return "index.html";
    }

    @RequestMapping(value = "/resume")
    @GetMapping
    public String resume(){
        //자기소개서 첫화면 불러오기
        return "";
    }

    @RequestMapping(value = "/save")
    @PostMapping
    public String save(@RequestParam("content") String content){
        //자기소개서 보내기
        return "";
    }

}
