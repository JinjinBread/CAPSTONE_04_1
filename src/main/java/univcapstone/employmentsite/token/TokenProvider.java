package univcapstone.employmentsite.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import univcapstone.employmentsite.dto.TokenDto;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static univcapstone.employmentsite.util.AuthConstants.*;

@Component
@Slf4j
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private Key key;

    private final CustomUserDetailsService customUserDetailsService;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            CustomUserDetailsService customUserDetailsService) {
        this.secret = secret;
        this.customUserDetailsService = customUserDetailsService;
    }

    // 빈이 생성되고 주입을 받은 후에 secret 값을 Base64 Decode해서 key 변수에 할당하기 위해
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //JWT 토큰 생성하는 메서드
    public TokenDto createToken(Authentication authentication) {
        //권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        //토큰의 expire 시간을 설정
        long now = (new Date()).getTime();

        //Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) //토큰 제목 정보 -> 여기에 loginId가 들어간다.
                .claim(AUTHORITIES_KEY, authorities) // 정보 저장
                .setExpiration(new Date(now + ACCESS_TOKEN_VALID_TIME)) //토큰 만료 시간 (해당 옵션 안 넣으면 만료 안 함)
                .signWith(key, SignatureAlgorithm.HS512) // 사용할 암호화 알고리즘과 , signature 에 들어갈 secret값 세팅
                .compact();

        //Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_VALID_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.builder()
                .grantType(BEARER_PREFIX)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //반대로 토큰을 받아서 토큰에 담겨 있는 권한 정보들을 이용해서 Authentication 객체를 리턴하는 메서드
    public Authentication getAuthentication(String accessToken) {
        //토큰 복호화
        Claims claims = parseClaims(accessToken); //토큰에 담겨 있는 권한 정보

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

//        User principal = new User(claims.getSubject(), "", authorities);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, accessToken, authorities); //유저 객체와 토큰, 권한 정보를 통해 Authentication 객체 리턴
    }

    //토큰의 유효성 검증을 수행
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {

            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {

            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {

            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {

            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public void setAccessTokenHeader(String accessToken, HttpServletResponse response) {
        String headerValue = BEARER_PREFIX + accessToken;
        response.setHeader(AUTH_HEADER, headerValue);
    }

//    public void refresshTokenSetHeader(String refreshToken, HttpServletResponse response) {
//        response.setHeader("Refresh", refreshToken);
//    }

    // Request Header에 Access Token 정보를 추출하는 메서드
    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Request Header에 Refresh Token 정보를 추출하는 메서드
//    public String resolveRefreshToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader(REFRESH_HEADER);
//        if (StringUtils.hasText(bearerToken)) {
//            return bearerToken;
//        }
//        return null;
//    }
}