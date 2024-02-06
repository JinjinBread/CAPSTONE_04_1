package univcapstone.employmentsite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import univcapstone.employmentsite.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**") //인터셉터를 모든 경로에 적용
                .excludePathPatterns(
                        "/", "/home", "/join", "/terms", "/login/**", "/logout",
                        "/confirm/**", "/verify/**", "/find/**"
                ); // /login, /confirm 포함 제외됨
    }
}
