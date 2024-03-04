package univcapstone.employmentsite.token;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * 인증이 안된 사용자가 인증이 필요한 URI에 접근하게 되면 보낼 응답 설정
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Not Authenticated Request", authException);
        log.error("Request Uri : {}", request.getRequestURI());

        resolver.resolveException(request, response, null, authException);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
//
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(errorResponse);
    }
}
