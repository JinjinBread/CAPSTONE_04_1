package univcapstone.employmentsite.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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

    @GetMapping("/home/saramin")
    public ResponseEntity<ResponseEntity<String>> saramin(){
        RestTemplate restTemplate=new RestTemplate();
        String apiUrl = "https://oapi.saramin.co.kr/job-search?access-key=pEyjyJB3XnowAZP5ImZUuNbcGwGGDbUGQXQfdDZqhSFgPkBXKWq&keywords=웹+퍼블리셔"; // 취업 사이트의 API URL
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        log.info("응답된 내용 = {}",response);

        return ResponseEntity.ok()
                .body(response);
    }
    //닉네임 유저아이디
    @GetMapping("/home")
    public ResponseEntity<? extends BasicResponse> home(Authentication authentication) {
        log.info("current user login_id = {}", authentication.getName());
        Optional<User> users=userRepository.findByLoginId(authentication.getName());
        User user=users.get();
        if(user==null){
            log.info("유저를 찾을 수 없습니다.");
        }


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
