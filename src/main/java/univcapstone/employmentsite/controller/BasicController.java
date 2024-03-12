package univcapstone.employmentsite.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import univcapstone.employmentsite.domain.Reply;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.ImageAPIHrefDto;
import univcapstone.employmentsite.dto.JobResponseDto;
import univcapstone.employmentsite.dto.ReplyToFrontDto;
import univcapstone.employmentsite.repository.UserRepository;
import univcapstone.employmentsite.service.UserService;
import univcapstone.employmentsite.token.CustomUserDetails;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


@Slf4j
@RestController
@RequiredArgsConstructor
public class BasicController {

    private final UserService userService;

    @GetMapping("/home/saramin")
    public ResponseEntity<List<JobResponseDto.Job>> saramin() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://oapi.saramin.co.kr/job-search?access-key=pEyjyJB3XnowAZP5ImZUuNbcGwGGDbUGQXQfdDZqhSFgPkBXKWq&bbs_gb=1&sr=directhire&job_type=1,10&loc_cd=117000&sort=rc&start=0&count=12&fields=expiration-date"; // 취업 사이트의 API URL
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        log.info("응답된 내용 = {}", response);
        String responseBody = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        JobResponseDto jobResponse = mapper.readValue(responseBody, JobResponseDto.class);
        List<JobResponseDto.Job> jobs = jobResponse.getJobs().getJob();
        for (int i=0; i<jobs.size(); i++) {
            log.info("expiration-date = {}",jobs.get(i).getExpirationDate().toString());
            jobs.get(i).setDday(jobs.get(i).getExpirationDate().toString());
        }

        return ResponseEntity.ok()
                .body(jobs);
    }

    @GetMapping("/home/saramin/href")
    public ResponseEntity<List<String>> extractImages() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://oapi.saramin.co.kr/job-search?access-key=pEyjyJB3XnowAZP5ImZUuNbcGwGGDbUGQXQfdDZqhSFgPkBXKWq&bbs_gb=1&sr=directhire&job_type=1,10&loc_cd=117000&sort=rc&start=0&count=12&fields=expiration-date"; // 취업 사이트의 API URL
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        log.info("응답된 내용 = {}", response);
        String responseBody = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        List<String> hrefList = new ArrayList<>();
        List<String> imageUrlList = new ArrayList<>();

        if (responseBody != null) {
            ImageAPIHrefDto imageAPIResponse = mapper.readValue(responseBody, ImageAPIHrefDto.class);

            List<ImageAPIHrefDto.Job> jobs = imageAPIResponse.getJobs().getJob();
            for (ImageAPIHrefDto.Job job : jobs) {
                if (job.getCompany() != null && job.getCompany().getDetail() != null) {
                    String href = job.getCompany().getDetail().getHref();
                    try {
                        Document document = Jsoup.connect(href).get();
                        Elements elements = document.select("div.box_logo img");

                        StringBuilder images = new StringBuilder();
                        log.info("Elements = {}", elements);

                        for (Element element : elements) {
                            String imageUrl = element.attr("src");
                            images.append(imageUrl);
                            //images.append("<img src='").append(imageUrl).append("' />");
                        }

                        imageUrlList.add(images.toString());
                    } catch (HttpStatusException e) {
                        imageUrlList.add(null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    hrefList.add(href);
                }
            }
        }
        log.info("hef URL 데이터 = {}", hrefList);
        log.info("이미지 URL 데이터 = {}", imageUrlList);
        return ResponseEntity.ok()
                .body(imageUrlList);
    }

    @GetMapping("/home")
    public ResponseEntity<? extends BasicResponse> home(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {

        User user = userService.findUserByLoginId(customUserDetails.getUsername());

        log.info("current user = {}", user);

        if (user == null) {
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
