package univcapstone.employmentsite.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import univcapstone.employmentsite.dto.PostToFrontDto;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class PictureController {
    @GetMapping("/profile/male")
    public String malePicture(){
        //취업사진 남성용
        return "";
    }

    @GetMapping("/profile/female")
    public String femalePicture(){
        //취업사진 여성용
        return "";
    }

    @PostMapping("/profile/edit")
    public ResponseEntity<? extends BasicResponse> editPicture(
            @RequestParam("photoId") String photo_id,
            @RequestParam("brightness") float brightness,
            @RequestParam("saturation") float saturation
    ){
        // 1. Spring 에서 사진 아이디로 사진을 찾고,
        // 2. 찾은 사진과 Front에서 온 명도와 채도 등의 값들을 해당 사진을 Flask로 보냄
        // 3. Flask에서 GAN을 이용해서 데이터를 생성하던지, 명도 채도 값을 조절한 사진을 보냄
        // 4. Spring에서 Flask에서 온 사진을 받아 Front로 전달

        // 이미지 파일을 file.getBytes()을 이용해서 바이트 배열로 변환(현재는 일단 임의로 배열 생성)
        byte[] imageBytes = new byte[1000];
        // JSON 형식으로 줄 이미지,명도,채도
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("file", imageBytes);
        formData.add("brightness", brightness);
        formData.add("saturation", saturation);

        // Flask server로 데이터를 주고 받기
        RestTemplate restTemplate = new RestTemplate();
        String flaskEndpoint = "http://localhost:12300/profile/edit";
        String result = restTemplate.postForObject(flaskEndpoint, formData, String.class);

        log.info("Flask에서 온 응답 {}",result);

        DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("Flask에서 온 데이터")
                .result(result)
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);

    }

    @GetMapping("/profile/guide")
    public String editGuide(){
        //합성 가이드
        return "";
    }

    @GetMapping("/profile/save")
    public String save(){
        //이미지 저장
        return "";
    }
}
