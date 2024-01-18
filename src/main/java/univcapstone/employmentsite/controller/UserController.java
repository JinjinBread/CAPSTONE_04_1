package univcapstone.employmentsite.controller;

import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.domain.User;
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
    public ResponseEntity<String> verifyID(@RequestBody String userId, HttpServletRequest request) {
        try {
            userService.validateDuplicateLoginId(userId);
            // 중복이 없는 경우, 사용 가능한 ID로 간주
            return ResponseEntity.ok().body("사용 가능한 ID");
        } catch (IllegalStateException e) {
            // 중복이 있는 경우, 예외가 발생하면 이 부분이 실행됨
            return ResponseEntity.ok().body("이미 사용중인 ID 입니다.");
        }
    }

    @GetMapping("/find/id")
    public String findID() {
        return "findID"; //아이디 찾기 페이지
    }
    @PostMapping("/find/id")
    public ResponseEntity<String> findID(@RequestBody String name,@RequestBody String email) {
        User user=userService.findId(name,email);
        if(user==null){
            return ResponseEntity.ok().body("해당하는 이름과 Email의 계정이 없습니다.");
        }
        else{
            return ResponseEntity.ok().body(user.getLoginId());
        }
    }

    @PostMapping("/find/pw")
    public ResponseEntity<String> findPassword(
            @RequestBody String userId,
            @RequestBody String name,
            @RequestBody String email
    ) {
        //비밀번호 찾기에 대한 로직
        User user=userService.findPassword(userId,name,email);
        if(user==null){
            return ResponseEntity.ok().body("해당하는 이름과 Email의 계정이 없습니다.");
        }
        else{
            return ResponseEntity.ok().body(user.getPassword());
        }
    }

    @PostMapping("/reset/pw")
    public ResponseEntity<String> resetPassword(@RequestBody String password) {
        try{
            userService.updatePassword(SessionConst.LOGIN_USER_ID,password);
            return ResponseEntity.ok().body("비밀번호 변경완료");
        }catch (PersistenceException e){
            return ResponseEntity.ok().body("비밀번호 변경실패");
        }
    }
}
