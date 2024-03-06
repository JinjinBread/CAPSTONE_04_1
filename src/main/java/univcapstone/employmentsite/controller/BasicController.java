package univcapstone.employmentsite.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.JobResponseDto;
import univcapstone.employmentsite.dto.PostToFrontDto;
import univcapstone.employmentsite.repository.UserRepository;
import univcapstone.employmentsite.service.AuthService;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@RestController
@RequiredArgsConstructor
public class BasicController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/home/saramin")
    public ResponseEntity<List<JobResponseDto.Job>> saramin(
            @RequestBody Map<String, String> receiverMap
    ) throws JsonProcessingException {
        String keyword=receiverMap.get("keyword");
        RestTemplate restTemplate=new RestTemplate();
        String apiUrl = "https://oapi.saramin.co.kr/job-search?access-key=pEyjyJB3XnowAZP5ImZUuNbcGwGGDbUGQXQfdDZqhSFgPkBXKWq&keywords="+keyword; // 취업 사이트의 API URL
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        log.info("응답된 내용 = {}",response);
        String responseBody=response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        JobResponseDto jobResponse = mapper.readValue(responseBody, JobResponseDto.class);
        List<JobResponseDto.Job> jobs = jobResponse.getJobs().getJob();
        return ResponseEntity.ok()
                .body(jobs);
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
