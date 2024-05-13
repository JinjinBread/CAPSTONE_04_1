package univcapstone.employmentsite.oauth2.handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import univcapstone.employmentsite.domain.Authority;
import univcapstone.employmentsite.domain.RefreshToken;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.oauth2.*;
import univcapstone.employmentsite.oauth2.utils.CookieUtil;
import univcapstone.employmentsite.repository.RefreshTokenRepository;
import univcapstone.employmentsite.repository.UserRepository;
import univcapstone.employmentsite.token.TokenProvider;
import univcapstone.employmentsite.util.AuthConstants;
import univcapstone.employmentsite.util.Constants;

import java.io.IOException;
import java.util.Optional;

import static univcapstone.employmentsite.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.MODE_PARAM_COOKIE_NAME;
import static univcapstone.employmentsite.util.AuthConstants.*;
import static univcapstone.employmentsite.util.Constants.DOMAIN;

@RequiredArgsConstructor
@Slf4j
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final OAuth2UnlinkManager oAuth2UserUnlinkManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String targetUrl;

        targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {

        String mode = CookieUtil.getCookie(request, MODE_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("");

        CustomOAuth2User customOAuth2User = getOAuth2UserPrincipal(authentication);


        if (customOAuth2User == null) {
            return UriComponentsBuilder.fromUriString(OAUTH_REDIRECT_URI)
                    .queryParam("error", "Login failed")
                    .build().toUriString();
        }

        Optional<User> optionalUser = userRepository.findByEmail(customOAuth2User.getUsername());

        if ("login".equalsIgnoreCase(mode)) {
            // TODO: DB 저장
            // TODO: 액세스 토큰, 리프레시 토큰 발급
            // TODO: 리프레시 토큰 DB 저장
            OAuth2UserInfo userInfo = customOAuth2User.getUserInfo();

            log.info("email={}, name={}, nickname={}, accessToken={}", customOAuth2User.getUserInfo().getEmail(),
                    userInfo.getName(),
                    userInfo.getNickname(),
                    userInfo.getAccessToken()
            );

            //첫 회원가입 시에만 DB 저장
            if (optionalUser.isEmpty()) {
                User user = User.builder()
                        .loginId(userInfo.getEmail())
                        .nickname(userInfo.getNickname())
                        .email(userInfo.getEmail())
                        .name(userInfo.getName())
                        .provider(userInfo.getProvider())
                        .authority(Authority.USER)
                        .build();

                userRepository.save(user);
            }

            String accessToken = tokenProvider.createAccessToken(authentication, ACCESS_TOKEN_VALID_TIME);
            String refreshToken = tokenProvider.createRefreshToken(authentication, REFRESH_TOKEN_VALID_TIME);

            return UriComponentsBuilder.fromUriString(OAUTH_REDIRECT_URI)
                    .queryParam("accessToken", accessToken)
                    .queryParam("refreshToken", refreshToken)
                    .build().toUriString();

        } else if ("unlink".equalsIgnoreCase(mode)) {

            OAuth2UserInfo oAuth2UserInfo = customOAuth2User.getUserInfo();
            OAuth2Provider provider = oAuth2UserInfo.getProvider();

            String accessToken = oAuth2UserInfo.getAccessToken();

            User user = userRepository.findByEmail(oAuth2UserInfo.getEmail())
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

            // TODO: DB 삭제
            userRepository.delete(user);

            // TODO: 리프레시 토큰 삭제
            String strRefreshToken = tokenProvider.resolveRefreshToken(request);
            RefreshToken refreshToken = refreshTokenRepository.findRefreshTokenByRefreshToken(strRefreshToken)
                    .orElseThrow(() -> new RuntimeException("Refresh Token이 존재하지 않습니다."));

            refreshTokenRepository.delete(refreshToken);

            oAuth2UserUnlinkManager.unlink(provider, accessToken);

            return UriComponentsBuilder.fromUriString(DOMAIN)
                    .build().toUriString();
        }

        return UriComponentsBuilder.fromUriString(OAUTH_REDIRECT_URI)
                .queryParam("error", "Login failed")
                .build().toUriString();
    }

    private CustomOAuth2User getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomOAuth2User) {
            return (CustomOAuth2User) principal;
        }
        return null;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
