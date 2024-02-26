package univcapstone.employmentsite.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import univcapstone.employmentsite.config.filter.JwtRequestFilter;
import univcapstone.employmentsite.token.TokenProvider;

@RequiredArgsConstructor
@Configuration
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        // security 로직에 JwtFilter 등록
        httpSecurity.addFilterBefore(
                new JwtRequestFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class);
    }
}
