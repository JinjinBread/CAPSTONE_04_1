package univcapstone.employmentsite.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.domain.Resume;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.ResumeDto;
import univcapstone.employmentsite.service.ResumeService;
import univcapstone.employmentsite.service.UserService;
import univcapstone.employmentsite.token.CustomUserDetails;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;

import java.util.List;

@Slf4j
@RestController
public class ResumeController {

    private final UserService userService;
    private final ResumeService resumeService;

    @Autowired
    public ResumeController(UserService userService, ResumeService resumeService) {
        this.userService = userService;
        this.resumeService = resumeService;
    }

    @GetMapping(value = "/resume/write")
    public ResponseEntity<? extends BasicResponse> resumeWrite() {
        //자기소개서 첫화면 불러오기
        DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("자기소개서 첫화면")
                .result("")
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);
    }

    @GetMapping(value = "/resume/revise")
    public ResponseEntity<? extends BasicResponse> getResumeRevise(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {

        User user = customUserDetails.getUser();

        //자기소개서 수정하기 불러오기
        List<Resume> resumes = resumeService.getMyResume(user.getId());
        log.info("가져오려는 자기소개서들 {}", resumes);

        DefaultResponse<List<Resume>> defaultResponse = DefaultResponse.<List<Resume>>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("수정할 자기소개서 가져오기 완료")
                .result(resumes)
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);
    }

    @PostMapping(value = "/resume/revise")
    public ResponseEntity<? extends BasicResponse> postResumeRevise(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Validated ResumeDto resumeDto
    ) {

        User user = customUserDetails.getUser();

        //자기소개서 수정하기
        resumeService.reviseResume(resumeDto.getResumeId(), resumeDto.getContent());
        log.info("수정자: {}, 수정 내용 {}", user, resumeDto);

        DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("자기소개서 수정 완료")
                .result("")
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);
    }

    @PostMapping(value = "/resume/save")
    public ResponseEntity<? extends BasicResponse> resumeSave(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Validated ResumeDto resumeDto
    ) {

        User user = customUserDetails.getUser();

        //자기소개서 저장하기
        Resume resume = resumeService.saveResume(user, resumeDto.getContent());

        log.info("저장하려는 사람 {}, 저장하는 내용 {}", user, resumeDto);

        DefaultResponse<Resume> defaultResponse = DefaultResponse.<Resume>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("자기소개서 저장 완료")
                .result(resume)
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);

    }

}