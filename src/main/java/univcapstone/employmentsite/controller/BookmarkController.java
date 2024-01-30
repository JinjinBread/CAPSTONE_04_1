package univcapstone.employmentsite.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.dto.BookmarkDeleteDto;
import univcapstone.employmentsite.service.BookmarkService;
import univcapstone.employmentsite.service.UserService;
import univcapstone.employmentsite.util.SessionConst;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;
import univcapstone.employmentsite.util.response.ErrorResponse;

@Slf4j
@RestController
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    /**
     * 자신의 북마크 가져오기
     * @param postId
     * @param userLoginId
     * @return
     */
    @GetMapping("/boardlist/{postId}/bookmark")
    public String bookmark(
            @PathVariable Long postId,
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) String userLoginId
    ){
        //게시글 북마크
        return "";
    }

    /**
     * 북마크 삭제
     * @param bookmarkData
     * @return
     */
    @DeleteMapping(value="/user/bookmark/delete")
    public ResponseEntity<? extends BasicResponse> editUser(
            @RequestBody @Validated BookmarkDeleteDto bookmarkData
    ){
        try{
            bookmarkService.deleteBookmark(bookmarkData.getBookmarkId(),bookmarkData.getPostId());

            DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("북마크 삭제완료.")
                    .result("")
                    .build();

            return ResponseEntity.ok().body(defaultResponse);
        }catch (IllegalStateException e){
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "북마크 삭제 실패"));
        }

    }

}