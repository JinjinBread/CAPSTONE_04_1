package univcapstone.employmentsite.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.dto.ReplyPostDto;
import univcapstone.employmentsite.service.ReplyService;

@Slf4j
@RestController
public class ReplyController {
    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }
    /**
     * 댓글 달기
     * @param postId
     * @param replyData
     * @return
     */
    @PostMapping("/boardlist/{postId}/{replyId}")
    public String reply(
            @PathVariable Long postId,
            @RequestBody @Validated ReplyPostDto replyData
    ){

        return "";

    }

    /**
     * 댓글 삭제
     * @param postId
     * @param replyId
     * @return
     */
    @DeleteMapping("/boardlist/{postId}/{replyId}")
    public String replyDelete(
            @PathVariable Long postId,
            @PathVariable Long replyId
    ){
        //댓글삭제.. 대댓글이 있다면 삭제안되도록 해야하나?..
        return "";
    }
}
