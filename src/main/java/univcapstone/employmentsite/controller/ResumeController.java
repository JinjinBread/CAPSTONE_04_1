package univcapstone.employmentsite.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.domain.Resume;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.ResumeDto;
import univcapstone.employmentsite.service.ResumeService;
import univcapstone.employmentsite.service.UserService;
import univcapstone.employmentsite.util.SessionConst;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;

@Slf4j
@RestController
public class ResumeController {

    private final UserService userService;
    private final ResumeService resumeService;

    @Autowired
    public ResumeController(UserService userService,ResumeService resumeService) {
        this.userService = userService;
        this.resumeService = resumeService;
    }

    @GetMapping(value = "/resume/write")
    public String resumeWrite(){
        //자기소개서 첫화면 불러오기
        return "";
    }

    @GetMapping(value = "/resume/revise")
    public ResponseEntity<? extends BasicResponse> getResumeRevise(
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser
    ){
        //자기소개서 수정하기 불러오기
        User user=userService.findUserByLoginId(loginUser.getLoginId());
        Resume resume=resumeService.getMyResume(user.getId());
        log.info("가져오려는 자기소개서 정보 {}", resume);

        DefaultResponse<Resume> defaultResponse = DefaultResponse.<Resume>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("자기소개서 가져오기 완료")
                .result(resume)
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);
    }

    @PostMapping(value = "/resume/revise")
    public String postResumeRevise(
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser,
            @RequestBody @Validated ResumeDto resumeDto
    ){
        //자기소개서 수정하기
        User user=userService.findUserByLoginId(loginUser.getLoginId());
        resumeService.reviseResume(user, resumeDto.getContent());
        log.info("수정자: {}, 수정 내용 {}", user, resumeDto);
        return "";
    }

    @PostMapping(value = "/resume/save")
    public ResponseEntity<? extends BasicResponse> resumeSave(
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser,
            @RequestBody @Validated ResumeDto resumeDto
    ){
        //자기소개서 저장하기
        User user=userService.findUserByLoginId(loginUser.getLoginId());
        resumeService.saveResume(user, resumeDto.getContent());
        log.info("저장하려는 사람 {}, 저장하는 내용 {}",
                user, resumeDto);

        DefaultResponse<User> defaultResponse = DefaultResponse.<User>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("자기소개서 저장 완료")
                .result(user)
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);

    }

}