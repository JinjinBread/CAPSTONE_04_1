package univcapstone.employmentsite.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import univcapstone.employmentsite.config.filter.JwtRequestFilter;
import univcapstone.employmentsite.token.JwtAccessDeniedHandler;
import univcapstone.employmentsite.token.JwtAuthenticationEntryPoint;
import univcapstone.employmentsite.token.TokenProvider;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // PasswordEncoder는 BCryptPasswordEncoder를 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) //HTTP 기본 인증 및 CSRF 보안 비활성화, CORS 활성화

                //세션 기반 인증 사용 끔(STATELESS로 설정)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .exceptionHandling(requests -> requests
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests //요청 인증 무시 목록
                                .requestMatchers("/", "/join", "/joincheck",
                                        "/login/**", "/logout", "/confirm/**",
                                        "/verify/**", "/find/**", "/favicon.ico").permitAll()
                                //회원가입, 개인정보 동의, 로그인, 로그아웃, 이메일 인증, 아이디 중복 확인, 아이디 및 비밀번호 찾기, 파비콘
                                .anyRequest().authenticated()
                ) //위 URI 외의 URI는 모두 인증 필수

                .formLogin(AbstractHttpConfigurer::disable)

//                .addFilterBefore()
                .with(new JwtSecurityConfig(tokenProvider), Customizer.withDefaults())
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("https://localhost:3000"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}