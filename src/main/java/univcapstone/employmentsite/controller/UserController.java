package univcapstone.employmentsite.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.domain.User;
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

    @PostMapping("/join")
    public ResponseEntity<? extends BasicResponse> join(@RequestBody @Validated User user, BindingResult bindingResult) {
        //회원가입에 대한 로직
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "회원가입 실패"));
        }

        userService.join(user);
        log.info("[{}] loginId={}, password={}, name={}, email={}, nickname={}",
                user.getId(), user.getLoginId(), user.getPassword(), user.getName(), user.getEmail(), user.getNickname());

        DefaultResponse<User> defaultResponse = DefaultResponse.<User>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("회원가입 완료")
                .result(user)
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);
    }

    @PostMapping("/verify/id")
    public String verifyID() {
        //아이디 중복 확인에 대한 로직
        return ""; //아이디 중복확인
    }

    @GetMapping("/find/id")
    public String findID() {
        //아이디 찾기 페이지
        return "findID"; //아이디 찾기 페이지
    }
    @PostMapping("/find/id")
    public String findID(String email) {
        //아이디 찾기에 대한 로직
        return "findID";
    }

    @PostMapping("/find/pw")
    public String findPassword() {
        //비밀번호 찾기에 대한 로직
        return "findPW"; //비밀번호 찾기 페이지
    }
}
