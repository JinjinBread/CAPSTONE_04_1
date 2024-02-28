package univcapstone.employmentsite.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import univcapstone.employmentsite.dto.TokenDto;
import univcapstone.employmentsite.dto.TokenRequestDto;
import univcapstone.employmentsite.dto.UserRequestDto;
import univcapstone.employmentsite.dto.UserResponseDto;
import univcapstone.employmentsite.service.AuthService;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;
import univcapstone.employmentsite.util.response.ErrorResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입
     * @param userRequestDto
     * @param bindingResult
     * @return
     */
    @PostMapping("/join")
    public ResponseEntity<UserResponseDto> join(@RequestBody @Validated UserRequestDto userRequestDto, BindingResult bindingResult) {
        //회원가입에 대한 로직
//        if (bindingResult.hasErrors()) {
//            log.error("join binding fail = {}", bindingResult);
//            return ResponseEntity.badRequest();
//        }
        
        return ResponseEntity.ok(authService.join(userRequestDto));
    }

    /**
     * 로그인
     * @param userRequestDto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<TokenDto> login(@RequestBody @Validated UserRequestDto userRequestDto) {

        //보안 상 서버 단에서 아이디와 패스워드 유효성 검사 필요(UsernamePasswordAuthenticationFilter)

        return ResponseEntity.ok(authService.login(userRequestDto));

    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody @Validated TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
