package univcapstone.employmentsite.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jobhak.univ")
public class ResumeController {

    @GetMapping(value = "/resume/write")
    public String resumeWrite(){
        //자기소개서 첫화면 불러오기
        return "";
    }

    @GetMapping(value = "/resume/revise")
    public String getResumeRevise(){
        //자기소개서 첫화면 불러오기
        return "";
    }

    @PostMapping(value = "/resume/revise")
    public String postResumeRevise(){
        //자기소개서 첫화면 불러오기
        return "";
    }

    @PostMapping(value = "/resume/save")
    public String resumeSave(@RequestParam("content") String content){
        //자기소개서 보내기
        return "";
    }

}
