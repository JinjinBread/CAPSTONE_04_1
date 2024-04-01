package univcapstone.employmentsite.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.dto.NaverLoginDto;
import univcapstone.employmentsite.dto.TokenDto;
import univcapstone.employmentsite.dto.UserRequestDto;
import univcapstone.employmentsite.dto.UserResponseDto;
import univcapstone.employmentsite.service.AuthService;
import univcapstone.employmentsite.service.UserService;
import univcapstone.employmentsite.token.TokenProvider;
import univcapstone.employmentsite.util.response.BasicResponse;

import java.util.Arrays;

import static univcapstone.employmentsite.util.AuthConstants.REFRESH_COOKIE_NAME;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final UserService userService;
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
    @GetMapping("/login/naver")
    public ResponseEntity<TokenDto> naverLogin(@RequestBody NaverLoginDto naverLoginDto){
        UserRequestDto socialData=new UserRequestDto();
        socialData.setLoginId(naverLoginDto.getLoginId());
        socialData.setPassword("");
        socialData.setName(naverLoginDto.getNickname());
        socialData.setEmail(naverLoginDto.getEmail());

        if(userService.findUserByLoginId(socialData.getLoginId())==null){
            UserResponseDto userResponseDto = authService.join(socialData);
            log.info("[{}] success join: {}", userResponseDto.getId(), userResponseDto);
            TokenDto tokenDto = authService.login(socialData);
            log.info("login success");
            return ResponseEntity.ok(tokenDto);
        }else{
            TokenDto tokenDto = authService.login(socialData);
            log.info("login success");
            return ResponseEntity.ok(tokenDto);
        }
    }
//    @PostMapping("/login/kakao")
//    public ResponseEntity<? extends BasicResponse> loginKakao()

//    @GetMapping("/logout")
//    public ResponseEntity<? extends BasicResponse> logout(HttpServletRequest request) {
//        //logout URI는 필터에서 걸러지지 않는다.
//        //Access Token 유효성 검증
//        String accessToken = tokenProvider.resolveAccessToken(request);
//
//        if (tokenProvider.validateToken(accessToken)) {
//            return ResponseEntity.badRequest()
//                    .body(new ErrorResponse(request.getContextPath(), HttpStatus.BAD_REQUEST.value(), "test"));
//        }
//
//        return ResponseEntity.ok(
//                DefaultResponse.builder()
//                        .code(HttpStatus.OK.value())
//                        .httpStatus(HttpStatus.OK)
//                        .message("로그아웃 성공")
//                        .result(accessToken)
//                        .build());
//    }

    /**
     * 토큰 재발급
     * @param request
     * @return
     */
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(HttpServletRequest request) {

        String refreshToken = tokenProvider.getRefreshTokenFromCookies(request);

        return ResponseEntity.ok(authService.reissue(refreshToken));
    }
}
