package univcapstone.employmentsite.controller;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
public class PictureController {
    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
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

    @PostMapping("/profile/save")
    public ResponseEntity<? extends BasicResponse> save(
            HttpServletRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Nullable MultipartFile[] multipartFileList
    ) throws IOException {
        User user = customUserDetails.getUser();
        try {
            if (multipartFileList == null) {
                // 원래는 null이면 저장하지말고 에러처리 해야하나 일단 테스트 용도로 남김
                String filePath = "tommy.jpg";
                log.info("filePath = {}", filePath);
                // S3에 업로드 할 임의의 파일 객체 생성
                File uploadFile = new File(filePath);
                multipartFileList = new MultipartFile[1];
                multipartFileList[0] = convert(uploadFile);
                ResponseEntity<Object> result = pictureService.uploadMultipartFile(user.getId().toString(), multipartFileList);
                DefaultResponse<ResponseEntity<Object>> defaultResponse = DefaultResponse.<ResponseEntity<Object>>builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("아마존 S3 더미 사진 저장 완료")
                        .result(result)
                        .build();


                return ResponseEntity.ok()
                        .body(defaultResponse);
            } else {
                ResponseEntity<Object> result = pictureService.uploadMultipartFile(user.getId().toString(), multipartFileList);
                DefaultResponse<ResponseEntity<Object>> defaultResponse = DefaultResponse.<ResponseEntity<Object>>builder()
                        .code(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .message("아마존 S3 요청 사진 저장 완료")
                        .result(result)
                        .build();


                return ResponseEntity.ok()
                        .body(defaultResponse);
            }

        } catch (Exception e) {
            log.info("에러 = {} ", e);
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(request.getServletPath(),
                            HttpStatus.BAD_REQUEST.value(),
                            "잘못된 저장 요청입니다."));
        }
    }


    public static MultipartFile convert(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = StreamUtils.copyToByteArray(fileInputStream);
        fileInputStream.close();
        return new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                return "application/octet-stream";
            }

            @Override
            public boolean isEmpty() {
                return bytes == null || bytes.length == 0;
            }

            @Override
            public long getSize() {
                return bytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return bytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                new FileOutputStream(dest).write(bytes);
            }
        };
    }
}
