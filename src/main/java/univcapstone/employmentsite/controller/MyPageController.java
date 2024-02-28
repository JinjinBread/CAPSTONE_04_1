package univcapstone.employmentsite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.domain.Bookmark;
import univcapstone.employmentsite.domain.Post;
import univcapstone.employmentsite.domain.Reply;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.*;
import univcapstone.employmentsite.service.BookmarkService;
import univcapstone.employmentsite.service.UserService;
import univcapstone.employmentsite.util.SessionConst;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;
import univcapstone.employmentsite.util.response.ErrorResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class MyPageController {

    private final UserService userService;
    private final BookmarkService bookmarkService;

    @Autowired
    public MyPageController(UserService userService,BookmarkService bookmarkService) {
        this.userService = userService;
        this.bookmarkService=bookmarkService;
    }

    @GetMapping("/user/myInfo")
    public ResponseEntity<? extends BasicResponse> myInfo(
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user
    ) {

        log.info("세션으로 찾은 로그인 유저의 정보 = {}", user);

        DefaultResponse<User> defaultResponse = DefaultResponse.<User>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("유저 마이페이지")
                .result(user)
                .build();

        return ResponseEntity.ok().body(defaultResponse);
    }

    @GetMapping("/user/picture")
    public String myPicture(
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser
    ) {
        //나의 사진 화면
        return "";
    }

    @GetMapping("/user/bookmark")
    public ResponseEntity<? extends BasicResponse> myBookmark(
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser
    ) {
        //나의 북마크
        List<Bookmark> bookmarks= bookmarkService.getMyBookmark(loginUser.getId());
        List<BookmarkToFront> boomarkToFront=new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            boomarkToFront.add(new BookmarkToFront(
                    bookmark.getBookmarkId(),
                    bookmark.getUser().getId(),
                    bookmark.getUser().getLoginId(),
                    bookmark.getUser().getName(),
                    bookmark.getPost().getPostId(),
                    bookmark.getPost().getTitle(),
                    bookmark.getPost().getCategory(),
                    bookmark.getPost().getDate()
            ));
        }
        log.info("찾은 자기의 북마크={}",boomarkToFront);

        DefaultResponse<List<BookmarkToFront>> defaultResponse = DefaultResponse.<List<BookmarkToFront>>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("북마크 가져오기 완료")
                .result(boomarkToFront)
                .build();

        return ResponseEntity.ok().body(defaultResponse);
    }

    @DeleteMapping(value = "/user/delete")
    public ResponseEntity<? extends BasicResponse> deleteUser(
            HttpServletRequest request,
            @RequestBody @Validated UserDeleteDto userDeleteDto
    ) {
        try {
            log.info("삭제하려는 유저 데이터 {}", userDeleteDto);

            userService.deleteUser(userDeleteDto);

            DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("계정 삭제 완료")
                    .result("")
                    .build();

            HttpSession session = request.getSession(false);

            if (session != null) {
                session.invalidate();
            }

            return ResponseEntity.ok().body(defaultResponse);
        } catch (IllegalStateException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "잘못된 삭제 요청입니다."));
        }
    }

    @PatchMapping(value = "/user/edit/pw")
    public ResponseEntity<? extends BasicResponse> editPw(
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser,
            @RequestBody @Validated UserDeleteDto userDeleteDto
    ){
        String newPassword= userDeleteDto.getPassword();

        userService.editPass(loginUser,newPassword);

        DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("비밀번호 변경 완료")
                .result("")
                .build();

        return ResponseEntity.ok().body(defaultResponse);

    }

    @PatchMapping(value = "/user/edit")
    public ResponseEntity<? extends BasicResponse> editNickname(
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser,
            @RequestBody @Validated NicknameDto nicknameDto
    ){
        String newNickname=nicknameDto.getNickname();

        userService.editNickname(loginUser,newNickname);

        DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("닉네임 변경 완료")
                .result("")
                .build();

        return ResponseEntity.ok().body(defaultResponse);

    }
}
/*
@PatchMapping(value = "/user/edit")
public ResponseEntity<? extends BasicResponse> editUser(
        @RequestBody @Validated UserEditDto userEditData
) {
    try {
        log.info("수정려는 유저 데이터 {}", userEditData);
        userService.editUser(userEditData);

        DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("계정 편집 완료")
                .result("")
                .build();

        return ResponseEntity.ok().body(defaultResponse);
    } catch (IllegalStateException e) {
        log.info("수정하려는 유저가 없거나 찾을 수 없습니다");
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        "잘못된 수정 요청입니다."));
    }

}
*/
