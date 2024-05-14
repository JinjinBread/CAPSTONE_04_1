package univcapstone.employmentsite.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static univcapstone.employmentsite.util.AuthConstants.AUTH_HEADER;
import static univcapstone.employmentsite.util.AuthConstants.BEARER_PREFIX;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoOAuth2Unlink implements OAuth2Unlink {

    private static final String UNLINK_URL = "https://kapi.kakao.com/v1/user/unlink";
    private final RestTemplate restTemplate;

    @Override
    public void unlink(String accessToken) {

        String authorizationHeader = BEARER_PREFIX + accessToken;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTH_HEADER, authorizationHeader);

        //연결 끊기에 성공한 사용자의 회원번호 리턴
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                UNLINK_URL,
                HttpMethod.POST,
                new HttpEntity<>(httpHeaders),
                Map.class
        );

        log.info("카카오 회원탈퇴 = {}", responseEntity.getBody().get("id"));
    }
}
