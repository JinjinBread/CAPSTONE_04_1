package univcapstone.employmentsite.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.domain.RefreshToken;
import univcapstone.employmentsite.dto.TokenDto;
import univcapstone.employmentsite.dto.UserRequestDto;
import univcapstone.employmentsite.dto.UserResponseDto;
import univcapstone.employmentsite.service.AuthService;
import univcapstone.employmentsite.token.TokenProvider;

import java.util.Map;

import static univcapstone.employmentsite.util.AuthConstants.REFRESH_COOKIE_NAME;
import static univcapstone.employmentsite.util.AuthConstants.REFRESH_TOKEN_VALID_TIME;


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
    public ResponseEntity<TokenDto> login(@RequestBody @Validated UserRequestDto userRequestDto,
                                          HttpServletResponse response) {

        //보안 상 서버 단에서 아이디와 패스워드 유효성 검사 필요(UsernamePasswordAuthenticationFilter)
        TokenDto tokenDto = authService.login(userRequestDto);
        log.info("login success");

        //쿠키 저장
        Cookie cookie = new Cookie(REFRESH_COOKIE_NAME, tokenDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS에서만 사용
        cookie.setPath("/");
        cookie.setMaxAge((int) REFRESH_TOKEN_VALID_TIME); // 7일 유효

        response.addCookie(cookie);

        return ResponseEntity.ok(tokenDto);
    }

    /**
     * 토큰 재발급
     *
     * @param request
     * @return
     */
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request) {

        String rt = tokenProvider.resolveRefreshToken(request);
        RefreshToken refreshToken = authService.getRefreshToken(rt);

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                    "error", "invalid_token",
                    "error_description", "The refresh token is expired or invalid"
            ));
        }

        String loginId = tokenProvider.getLoginId(rt);//refreshToken에 저장된 subject 클레임(loginId 넣어 둠)을 뽑아옴
        return ResponseEntity.ok(authService.reissue(rt, loginId));
    }

}
