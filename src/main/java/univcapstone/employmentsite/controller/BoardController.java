package univcapstone.employmentsite.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jobhak.univ")
public class BoardController {
    @GetMapping
    public String home(){
        return "index.html";
    }

    @RequestMapping(value = "/boardlist")
    @GetMapping
    public String boardMain(){
        //게시글 메인화면 보기
        return "";
    }

    @RequestMapping(value = "/boardlist/{board_id}")
    @GetMapping
    public String board(@PathVariable int board_id){
        //클릭한 게시글의 상세내용을 보여주기(게시글 보기)
        return "";
    }

    @RequestMapping(value = "/boardlist/write")
    @PostMapping
    public String boardWrite(
            @RequestParam("post_id") String post_id,
            @RequestParam("user_id") String user_id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("file") String file
    ){
        //게시글 쓰기
        //post_id는 데이터베이스에서 임의의 기본키 아닌가?...
        return "";
    }

    @RequestMapping(value = "/boardlist/edit/{board_id}")
    @PostMapping
    public String edit(
            @RequestParam("post_id") String post_id,
            @RequestParam("user_id") String user_id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("file") String file
    ){
        //게시글 수정
        return "";
    }

    @RequestMapping(value="/boardlist/delete/{board_id}")
    @DeleteMapping
    public String delete(@RequestParam("post_id") String post_id){
        //게시글 삭제
        return "";
    }

    @RequestMapping(value="/boardlist/delete/{board_title}")
    @GetMapping
    public String search(@PathVariable String board_title){
        //게시글 검색 (제목으로)
        return "";
    }

    @RequestMapping(value="/boardlist/{board_id}/bookmark")
    @GetMapping
    public String bookmark(
            @RequestParam("post_id") String post_id,
            @RequestParam("user_id") String user_id,
            @PathVariable int board_id
    ){
        //게시글 북마크
        return "";
    }

    @RequestMapping(value = "/boardlist/{board_id}/{reply_id}")
    @PostMapping
    public String reply(
            @RequestParam("post_id") String post_id,
            @RequestParam("user_id") String user_id,
            @RequestParam("ref_id") String ref_id,
            @RequestParam("reply_content") String reply_content,
            @RequestParam("reply_ref_id") String reply_ref_id
    ){
        //댓글 달기
        return "";
    }

    @RequestMapping(value = "/boardlist/{board_id}/{reply_id}")
    @DeleteMapping
    public String replyDelete(){
        //댓글삭제.. 대댓글이 있다면 삭제안되도록 해야하나?..
        return "";
    }
}
