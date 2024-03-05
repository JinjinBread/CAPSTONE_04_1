package univcapstone.employmentsite.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import univcapstone.employmentsite.token.TokenProvider;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //request 헤더에서 토큰을 꺼냄
        String jwt = tokenProvider.resolveAccessToken(request);

        //토큰의 유효성을 검사한다.
        //정상 토큰이면 해당 토큰으로 Authentication을 가져와서 시큐리티 컨텍스트에 저장
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            tokenProvider.setAccessTokenHeader(jwt, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (StringUtils.hasText(jwt) != tokenProvider.validateToken(jwt)) {
            setResponse(response, new ErrorMessage(400, "토큰 불일치"));
            return;
        } else if (jwt == null) {
            setResponse(response, new ErrorMessage(400, "토큰이 없습니다"));
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void setResponse(HttpServletResponse response, ErrorMessage errorMessage) throws RuntimeException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("error", errorMessage.getCode());
        formData.add("message", errorMessage.getMessage());

        response.setStatus(errorMessage.getCode());
        response.getWriter().print(formData);
    }

}
