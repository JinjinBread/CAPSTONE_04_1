package univcapstone.employmentsite.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import univcapstone.employmentsite.service.MailService;

import java.util.Map;

@Slf4j
@RestController
public class MailController {

    public final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/confirm/email")
    public int confirmEmail(@RequestBody Map<String, String> receiverMap) {
        //이메일 인증에 대한 로직
        int authNumber = mailService.sendMail(receiverMap.get("receiver"));
        log.info("authNumber={}", authNumber);
        return authNumber;
//        return "redirect:/jobhak.univ"; // 이메일 인중 성공 후 메인 페이지로 리다이렉트
    }

}
