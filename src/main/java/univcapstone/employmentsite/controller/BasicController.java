package univcapstone.employmentsite.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.JobResponseDto;
import univcapstone.employmentsite.repository.UserRepository;
import univcapstone.employmentsite.token.CustomUserDetails;
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
    public ResponseEntity<List<JobResponseDto.Job>> saramin() throws JsonProcessingException {
        RestTemplate restTemplate=new RestTemplate();
        String apiUrl = "https://oapi.saramin.co.kr/job-search?access-key=pEyjyJB3XnowAZP5ImZUuNbcGwGGDbUGQXQfdDZqhSFgPkBXKWq&bbs_gb=1&sr=directhire&job_type=1,10&loc_cd=117000&sort=rc&start=0&count=12"; // 취업 사이트의 API URL
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        log.info("응답된 내용 = {}",response);
        String responseBody=response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        JobResponseDto jobResponse = mapper.readValue(responseBody, JobResponseDto.class);
        List<JobResponseDto.Job> jobs = jobResponse.getJobs().getJob();
        return ResponseEntity.ok()
                .body(jobs);
    }

    @GetMapping("/home")
    public ResponseEntity<? extends BasicResponse> home(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        log.info("current user = {}", customUserDetails.getUser());
        Optional<User> users=userRepository.findByLoginId(customUserDetails.getUser().getLoginId());
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
