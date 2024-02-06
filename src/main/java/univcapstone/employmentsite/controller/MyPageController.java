package univcapstone.employmentsite.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.UserEditDto;
import univcapstone.employmentsite.dto.UserFindDto;
import univcapstone.employmentsite.service.UserService;
import univcapstone.employmentsite.util.SessionConst;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;
import univcapstone.employmentsite.util.response.ErrorResponse;

@Slf4j
@RestController
public class MyPageController {

    private final UserService userService;

    @Autowired
    public MyPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/myinfo")
    public ResponseEntity<? extends BasicResponse> myInfo(
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser
    ) {

        log.info("loginUser = ", loginUser);

        //개인정보 화면
        String loginId = loginUser.getLoginId();
        User user = userService.findUserByLoginId(loginId);

        log.info("찾은 유저의 정보 {}", user);

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
    public String myBookmark(
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser
    ) {
        //나의 북마크
        return "";
    }

    @DeleteMapping(value = "/user/delete")
    public ResponseEntity<? extends BasicResponse> deleteUser(
            @RequestBody @Validated UserFindDto userFindData
    ) {
        try {
            log.info("삭제하러는 유저 데이터 {}", userFindData);
            userService.deleteUser(userFindData.getLoginId());

            DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("계정 삭제 완료")
                    .result("")
                    .build();

            return ResponseEntity.ok().body(defaultResponse);
        } catch (IllegalStateException e) {
            log.error("잘못된 삭제 요청: 삭제하려는 계정이 없거나 찾을 수 없습니다.");
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "잘못된 삭제 요청입니다."));
        }

    }

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
}
