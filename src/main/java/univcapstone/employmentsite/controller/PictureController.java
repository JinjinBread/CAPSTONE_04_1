package univcapstone.employmentsite.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jobhak.univ")
public class PictureController {
    @GetMapping
    public String home(){
        return "index.html";
    }

    @RequestMapping(value = "/profile/male")
    @GetMapping
    public String malePicture(){
        //취업사진 남성용
        return "";
    }

    @RequestMapping(value = "/profile/female")
    @GetMapping
    public String femalePicture(){
        //취업사진 여성용
        return "";
    }
    
    @RequestMapping(value="/profile/edit")
    @PostMapping
    public String editPicture(@RequestParam("photo_id") String photo_id){
        //사진 합성하기
        return "";
    }
    
    @RequestMapping(value = "/profile/guide")
    @GetMapping
    public String editGuide(){
        //합성 가이드
        return "";
    }

    @RequestMapping(value = "/profile/save")
    @GetMapping
    public String save(){
        //이미지 저장
        return "";
    }
}
