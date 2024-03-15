package univcapstone.employmentsite.controller;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.service.PictureService;
import univcapstone.employmentsite.token.CustomUserDetails;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;
import univcapstone.employmentsite.util.response.ErrorResponse;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.util.List;

@Slf4j
@RestController
public class PictureController {

    private final PictureService pictureService;
    private final String dirName;

    public PictureController(PictureService pictureService, @Value("${aws.s3.idPhoto.dirName}") String dirName) {
        this.pictureService = pictureService;
        this.dirName = dirName;
    }

    @GetMapping("/profile/male/{fileName}")
    public ResponseEntity<? extends BasicResponse> downloadMalePicture(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String fileName
    ) {
        User user = customUserDetails.getUser();
        return pictureService.getImage(user);
    }

    @GetMapping("/profile/female")
    public String femalePicture() {
        //취업사진 여성용
        return "";
    }

    @PostMapping("/profile/edit")
    public ResponseEntity<? extends BasicResponse> editPicture(
            @RequestParam("photoId") String photo_id,
            @RequestParam("brightness") float brightness,
            @RequestParam("saturation") float saturation
    ) {
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

        log.info("Flask에서 온 응답 {}", result);

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
    public String editGuide() {
        //합성 가이드
        return "";
    }

    //받을 수 있는 최대 파일 수를 정하는 게 좋을 것 같음
    @PostMapping(value = "/profile/save", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<? extends BasicResponse> save(
            HttpServletRequest request,
            @RequestPart(value = "files") @Nullable List<MultipartFile> multipartFiles) throws IOException {

        try {
            List<String> uploadImagesUrl = pictureService.uploadFile(multipartFiles, dirName);

            return ResponseEntity.ok(
                    DefaultResponse.builder()
                            .code(HttpStatus.OK.value())
                            .httpStatus(HttpStatus.OK)
                            .message("사진 업로드 완료")
                            .result(uploadImagesUrl)
                            .build()
            );
        } catch (FileNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(request.getServletPath(),
                            HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }

    }

}
