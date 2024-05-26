package univcapstone.employmentsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static univcapstone.employmentsite.util.AuthConstants.AUTH_HEADER;
import static univcapstone.employmentsite.util.Constants.*;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); //기본적으로 요청에 대한 응답으로 JSON 형식이 나간다.
        config.addAllowedOrigin(LOCAL_REACT_URL);
        config.addAllowedOrigin(DOMAIN);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        config.addExposedHeader(AUTH_HEADER);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
