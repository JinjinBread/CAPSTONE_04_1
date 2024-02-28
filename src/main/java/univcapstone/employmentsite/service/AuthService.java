package univcapstone.employmentsite.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import univcapstone.employmentsite.domain.Authority;
import univcapstone.employmentsite.domain.RefreshToken;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.TokenDto;
import univcapstone.employmentsite.dto.TokenRequestDto;
import univcapstone.employmentsite.dto.UserRequestDto;
import univcapstone.employmentsite.dto.UserResponseDto;
import univcapstone.employmentsite.repository.RefreshTokenRepository;
import univcapstone.employmentsite.repository.UserRepository;
import univcapstone.employmentsite.token.TokenProvider;

import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder; //인증 객체를 생성해주는 빌더
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserResponseDto join(UserRequestDto userRequestDto) {

        if (userRepository.existsByLoginId(userRequestDto.getLoginId())) {
            throw new RuntimeException("사용할 수 없는 아이디입니다. 다른 아이디를 입력해 주세요.");
        }

        User user = User.builder()
                .loginId(userRequestDto.getLoginId())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .nickname(userRequestDto.getNickname())
                .email(userRequestDto.getEmail())
                .name(userRequestDto.getName())
                .authority(Authority.ROLE_USER)
                .activated(true)
                .build();

        return new UserResponseDto(userRepository.save(user));
    }

    public TokenDto login(UserRequestDto userRequestDto) {

        //요청으로 넘어온 로그인 아이디와 비밀번호를 통해 인증 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userRequestDto.getLoginId(), userRequestDto.getPassword());

        //비밀번호가 일치하는지 검증
        //authenticate 메서드가 실행될 때 CustomUserDetailsService에 만든 loadUserByUsername 메서드가 실행된다.
        //여기서 일치하지 않으면 AuthenticationEntryPoint로 간다.
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //해당 authenticate(인증 정보)를 기반으로 JWT TOKEN 생성
        TokenDto tokenDto = tokenProvider.createToken(authenticate);

        //RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(userRequestDto.getLoginId())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }

    /**
     * JWT는 한 번 발급하면 만료되기 전까지 삭제할 수 없다.
     * 따라서 짧은 유효시간을 갖는 Access Token과 (접근에 관여하는 토큰)
     * 저장소에 저장해서 Access Token을 재발급이 가능한 Refresh Token이 있다. (재발급에 관여하는 토큰)
     *
     * @param tokenRequestDto (Access Token, Refresh Token)
     * @return
     */
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {

        //RefreshToken 유효성 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        //RefreshTokenRepository에서 유저 loginId를 기반으로 Refresh Token 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 유저입니다.")); //loginId에 해당하는 Refresh Token이 존재하는지 판단

        //가져온 Refresh Token과 요청으로 넘어 온 Refresh Token이 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        //위 사항들이 모두 검증되면 Refresh Token 재발급 진행
        TokenDto tokenDto = tokenProvider.createToken(authentication);

        //Refresh Token 저장소 업데이트
        RefreshToken updatedRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(updatedRefreshToken);

        return tokenDto;
    }

}
