package univcapstone.employmentsite.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jobhak.univ")
public class MyPageController {
    @GetMapping
    public String home(){
        return "index.html";
    }

    @RequestMapping(value = "/user/myinfo")
    @GetMapping
    public String myInfo(){
        //개인정보 화면
        return "";
    }

    @RequestMapping(value="user/picture")
    @GetMapping
    public String myPicture(){
        //나의 사진 화면
        return "";
    }
    
    @RequestMapping(value = "user/bookmark")
    @GetMapping
    public String myBookmark(){
        //나의 북마크
        return "";
    }
    
    @RequestMapping(value = "user/delete")
    @DeleteMapping
    public String deleteUser(@RequestParam("id") String id){
        //회원 탈퇴
        return "";
    }

    @RequestMapping(value = "user/edit")
    @PatchMapping
    public String edit(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw,
            @RequestParam("nickname") String nickname,
            @RequestParam("photo_id") String photo_id
    ){
        //개인 정보 수정

        return "";
    }

    @RequestMapping(value = "user/bookmark/delete")
    @DeleteMapping
    public String deleteBookmark(
            @RequestParam("id") String id,
            @RequestParam("bookmark_id") String bookmark_id
    ){
        //북마크 삭제
        return "";
    }
}
