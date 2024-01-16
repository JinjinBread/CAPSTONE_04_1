package univcapstone.employmentsite.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jobhak.univ")
public class PictureController {
    @GetMapping("/profile/male")
    public String malePicture(){
        //취업사진 남성용
        return "";
    }

    @GetMapping("/profile/female")
    public String femalePicture(){
        //취업사진 여성용
        return "";
    }

    @PostMapping("/profile/edit")
    public String editPicture(@RequestParam("photo_id") String photo_id){
        //사진 합성하기
        return "";
    }

    @GetMapping("/profile/guide")
    public String editGuide(){
        //합성 가이드
        return "";
    }

    @GetMapping("/profile/save")
    public String save(){
        //이미지 저장
        return "";
    }
}
