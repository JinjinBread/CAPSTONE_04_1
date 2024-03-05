package univcapstone.employmentsite.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.PostToFrontDto;
import univcapstone.employmentsite.repository.UserRepository;
import univcapstone.employmentsite.service.AuthService;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;

import java.util.Optional;


@Slf4j
@RestController
@RequiredArgsConstructor
public class BasicController {
    @Autowired
    UserRepository userRepository;

    //닉네임 유저아이디
    @GetMapping("/home")
    public ResponseEntity<? extends BasicResponse> home(Authentication authentication) {
        log.info("current user login_id = {}", authentication.getName());
        Optional<User> users=userRepository.findByLoginId(authentication.getName());
        User user=users.get();

        DefaultResponse<User> defaultResponse = DefaultResponse.<User>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("홈페이지")
                .result(user)
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);
    }

    @GetMapping("/")
    public String loginForm() {
        return "login form"; // 로그인 페이지
    }
}
