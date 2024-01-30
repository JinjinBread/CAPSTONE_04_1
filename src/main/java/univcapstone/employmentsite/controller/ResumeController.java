package univcapstone.employmentsite.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jobhak.univ")
public class ResumeController {

    @RequestMapping(value = "/resume/write")
    @GetMapping
    public String resumeWrite(){
        //자기소개서 첫화면 불러오기
        return "";
    }

    @RequestMapping(value = "/resume/revise")
    @GetMapping
    public String resumeRevise(){
        //자기소개서 첫화면 불러오기
        return "";
    }

    @RequestMapping(value = "/resume/save")
    @PostMapping
    public String resumeSave(@RequestParam("content") String content){
        //자기소개서 보내기
        return "";
    }

}
