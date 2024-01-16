package univcapstone.employmentsite.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jobhak.univ")
public class MyPageController {
    @GetMapping("/user/myinfo")
    public String myInfo(){
        //개인정보 화면
        return "";
    }

    @GetMapping("user/picture")
    public String myPicture(){
        //나의 사진 화면
        return "";
    }

    @GetMapping("user/bookmark")
    public String myBookmark(){
        //나의 북마크
        return "";
    }

    @DeleteMapping("user/delete")
    public String deleteUser(@RequestParam("id") String id){
        //회원 탈퇴
        return "";
    }

    @PatchMapping("user/edit")
    public String edit(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw,
            @RequestParam("nickname") String nickname,
            @RequestParam("photo_id") String photo_id
    ){
        //개인 정보 수정

        return "";
    }

    @DeleteMapping("user/bookmark/delete")
    public String deleteBookmark(
            @RequestParam("id") String id,
            @RequestParam("bookmark_id") String bookmark_id
    ){
        //북마크 삭제
        return "";
    }
}
