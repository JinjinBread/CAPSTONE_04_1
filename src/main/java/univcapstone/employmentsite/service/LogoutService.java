package univcapstone.employmentsite.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import univcapstone.employmentsite.token.TokenProvider;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutService implements LogoutHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String accessToken = tokenProvider.resolveAccessToken(request);

        if (accessToken == null) {
            throw new RuntimeException("이미 로그아웃 된 사용자입니다.");
        }

        Authentication auth = tokenProvider.getAuthentication(accessToken);

        log.info("[id={}] logout", auth.getName());
    }
}
