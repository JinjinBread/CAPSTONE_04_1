package univcapstone.employmentsite.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import univcapstone.employmentsite.config.filter.JwtRequestFilter;
import univcapstone.employmentsite.service.AuthService;
import univcapstone.employmentsite.service.LogoutService;
import univcapstone.employmentsite.token.JwtAccessDeniedHandler;
import univcapstone.employmentsite.token.JwtAuthenticationEntryPoint;
import univcapstone.employmentsite.token.TokenProvider;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final LogoutService logoutService;

    // PasswordEncoder는 BCryptPasswordEncoder를 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class) //HTTP 기본 인증 및 CSRF 보안 비활성화, CORS 활성화

                //세션 기반 인증 사용 끔(STATELESS로 설정)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                //예외 처리 클래스 등록
                .exceptionHandling(requests -> requests
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests //요청 인증 무시 목록
                                .requestMatchers("/", "/join", "/joincheck",
                                        "/login/**", "/logout", "/confirm/**",
                                        "/verify/**", "/find/**").permitAll()
                                //회원가입, 개인정보 동의, 로그인, 로그아웃, 이메일 인증, 아이디 중복 확인, 아이디 및 비밀번호 찾기, 파비콘
                                .anyRequest().authenticated()
                ) //위 URI 외의 URI는 모두 인증 필수

                .formLogin(AbstractHttpConfigurer::disable)
                .logout(logoutConfig -> logoutConfig
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutService)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))

                .addFilterBefore(
                        new JwtRequestFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}