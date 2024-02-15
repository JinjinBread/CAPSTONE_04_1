package univcapstone.employmentsite.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.domain.Reply;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.ReplyPostDto;
import univcapstone.employmentsite.service.ReplyService;
import univcapstone.employmentsite.util.SessionConst;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;

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
     *
     * @param postId
     * @param user
     * @param replyDto
     * @return
     */
    @PostMapping("/boardlist/detail/{postId}/reply")
    public ResponseEntity<? extends BasicResponse> reply(
            @PathVariable Long postId,
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user,
            @RequestBody @Validated ReplyPostDto replyDto) {

        replyService.saveReply(postId, user, replyDto);
        log.info("댓글을 작성한 포스트 id = {}, 댓글을 작성한 유저 = {}, 작성한 댓글 정보 = {}", postId, user, replyDto);

        DefaultResponse<ReplyPostDto> defaultResponse = DefaultResponse.<ReplyPostDto>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("댓글 작성")
                .result(replyDto)
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);

    }

    /**
     * 댓글 삭제
     *
     * @param postId
     * @param replyId
     * @return
     */
    @DeleteMapping("/boardlist/{postId}/reply/delete/{replyId}")
    public String replyDelete(
            @PathVariable Long postId,
            @PathVariable Long replyId
    ) {
        //댓글삭제.. 대댓글이 있다면 삭제안되도록 해야하나?..
        return "";
    }
}
