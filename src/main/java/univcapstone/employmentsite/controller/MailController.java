package univcapstone.employmentsite.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import univcapstone.employmentsite.dto.MailDto;
import univcapstone.employmentsite.service.MailService;
import univcapstone.employmentsite.service.UserService;
import univcapstone.employmentsite.util.Constants;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;
import univcapstone.employmentsite.util.response.ErrorResponse;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    private final UserService userService;

    @PostMapping("/confirm/email")
    public ResponseEntity<? extends BasicResponse> confirmEmail(@RequestBody Map<String, String> receiverMap,
                                                                HttpServletRequest request) {

        String email = receiverMap.get(Constants.EMAIL_KEY);

        //이메일 중복 검증
        try {
            userService.validateDuplicateEmail(email);
        } catch (IllegalStateException e) {
            log.error("중복된 이메일 존재 = {}", email);
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(request.getServletPath(),
                            HttpStatus.BAD_REQUEST.value(),
                            "이미 사용 중인 이메일 입니다."));
        }

        //이메일 인증에 대한 로직
        int authNumber = MailService.createNumber();

        String content = generateAuthCodeFormat(authNumber);

        MailDto mailDto = MailDto.builder()
                .receiver(email)
                .subject("회원 가입 인증 번호입니다.")
                .content(content)
                .build();

        mailService.sendMail(mailDto);

        DefaultResponse<Integer> defaultResponse = DefaultResponse.<Integer>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("인증 번호 발송 완료")
                .result(authNumber)
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);
    }

    private static String generateAuthCodeFormat(int authNumber) {

        return "<div style='margin:20px;'>" +
                "<h1> 안녕하세요. Job학다식입니다. </h1>" +
                "<br>" +
                "<p>아래 코드를 복사해 입력해주세요<p>" +
                "<br>" +
                "<p>감사합니다.<p>" +
                "<br>" +
                "<div align='center' style='border:1px solid black; font-family:verdana';>" +
                "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>" +
                "<div style='font-size:130%'>" +
                "CODE : <strong>" +
                authNumber +
                "</strong><div><br/>" +
                "</div>";
    }

}
