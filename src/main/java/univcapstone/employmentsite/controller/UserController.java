package univcapstone.employmentsite.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.UserFindDto;
import univcapstone.employmentsite.util.SessionConst;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.ErrorResponse;
import univcapstone.employmentsite.service.UserService;
import univcapstone.employmentsite.util.response.DefaultResponse;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/join")
    public String join() {
        return "Hello join page"; // 회원가입 폼
    }

    @GetMapping("/joincheck")
    public String joinCheck() {
        return "join check list";
    }

//    @GetMapping("/error")
//    public S

    @PostMapping("/verify/id")
    public ResponseEntity<? extends BasicResponse> verifyID(
            @RequestBody @Validated UserFindDto userFindData
    ) {
        try {
            userService.validateDuplicateLoginId(userFindData.getLoginId());

            log.info("사용 가능한 아이디 = {}", userFindData.getLoginId());
            // 중복이 없는 경우, 사용 가능한 ID로 간주
            DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("ID 사용 가능")
                    .result(null)
                    .build();

            return ResponseEntity.ok()
                    .body(defaultResponse);

        } catch (IllegalStateException e) {
            log.error("중복된 아이디 존재 = {}",userFindData.getLoginId());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "이미 ID가 존재합니다."));
        }
    }

    @GetMapping("/find/id")
    public String findID() {
        return "findID"; //아이디 찾기 페이지
    }

    @PostMapping("/find/id")
    public ResponseEntity<? extends BasicResponse> findID(
            @RequestBody @Validated UserFindDto userFindData
    ) {
        User user = userService.findId(userFindData.getName(),
                userFindData.getEmail());

        log.info("이름과 이메일로 찾은 아이디 = {}", user);

        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "해당하는 이름과 Email의 계정이 없습니다."));
        } else {
            DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("ID를 찾았습니다.")
                    .result(user.getLoginId())
                    .build();

            return ResponseEntity.ok()
                    .body(defaultResponse);
        }
    }

    @PostMapping("/find/pw")
    public ResponseEntity<? extends BasicResponse> findPassword(
            @RequestBody @Validated UserFindDto userFindData
    ) {
        //비밀번호 찾기에 대한 로직
        User user = userService.findPassword(userFindData.getLoginId(),
                userFindData.getName(),
                userFindData.getEmail());

        log.info("아이디와 이름, 이메일로 찾은 비밀번호 = {}", user);

        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            "비밀번호를 찾을 수 없습니다."));
        } else {
            DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("비밀번호를 찾았습니다.")
                    .result(user.getPassword())
                    .build();

            return ResponseEntity.ok().body(defaultResponse);
        }
    }

    @PostMapping(value = "/reset/pw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends BasicResponse> resetPassword(
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser,
            @RequestBody User user) {

        Long userId = loginUser.getId();

        userService.updatePassword(userId, user.getPassword());

        log.info("변경된 유저 = {}, 변경된 유저의 비밀번호 = {}", user, user.getPassword());

        DefaultResponse<User> defaultResponse = DefaultResponse.<User>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("비밀번호 변경완료.")
                .result(user)
                .build();

        return ResponseEntity.ok().body(defaultResponse);
//        catch (PersistenceException e) {
//            return ResponseEntity.badRequest()
//                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
//                            "비밀번호 변경실패"));
//        }
    }
}
