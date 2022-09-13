package cn.bukkit.sip.security;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecutiryConfiguration {
    @Bean
    @SneakyThrows
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            @Autowired AuthenicationManagerImpl authenicationManager,
                                            @Autowired CasdoorLoginFilter casdoorLoginFilter) {
        CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();
        cookieCsrfTokenRepository.setCookieHttpOnly(false);

        http.authorizeRequests(authorizeRequests ->
                authorizeRequests
                        .antMatchers("/test/**",
                                "/login/**",
                                "/img/**",
                                "/img/**").permitAll()
                        .anyRequest().authenticated()
        ).csrf((csrf) -> csrf.ignoringAntMatchers("/test/**", "/login/callback").csrfTokenRepository(cookieCsrfTokenRepository));
        http.addFilterBefore(casdoorLoginFilter, UsernamePasswordAuthenticationFilter.class);
        http.authenticationManager(authenicationManager);
        return http.build();
    }
}
