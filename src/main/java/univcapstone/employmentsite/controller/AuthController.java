package univcapstone.employmentsite.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.dto.TokenDto;
import univcapstone.employmentsite.dto.UserRequestDto;
import univcapstone.employmentsite.dto.UserResponseDto;
import univcapstone.employmentsite.service.AuthService;
import univcapstone.employmentsite.token.TokenProvider;

import static univcapstone.employmentsite.util.AuthConstants.REFRESH_HEADER;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    /**
     * 회원가입
     *
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
        UserResponseDto userResponseDto = authService.join(userRequestDto);
        log.info("[{}] success join: {}", userResponseDto.getId(), userResponseDto);
        return ResponseEntity.ok(userResponseDto);
    }

    /**
     * 로그인
     *
     * @param userRequestDto
     * @return 액세스 토큰, 리프레시 토큰
     */
    @PostMapping("/")
    public ResponseEntity<TokenDto> login(@RequestBody @Validated UserRequestDto userRequestDto) {

        //보안 상 서버 단에서 아이디와 패스워드 유효성 검사 필요(UsernamePasswordAuthenticationFilter)
        TokenDto tokenDto = authService.login(userRequestDto);
        log.info("login success");
        return ResponseEntity.ok(tokenDto);
    }

    /**
     * 토큰 재발급
     *
     * @param request
     * @return
     */
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(HttpServletRequest request, Authentication authentication) {

        String refreshToken = tokenProvider.resolveRefreshToken(request);
        return ResponseEntity.ok(authService.reissue(refreshToken, authentication));
    }

}
