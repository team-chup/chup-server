package gsm.gsmjava.global.security.config;

import gsm.gsmjava.domain.user.type.Authority;
import gsm.gsmjava.global.filter.ExceptionHandlerFilter;
import gsm.gsmjava.global.filter.LoggingFilter;
import gsm.gsmjava.global.security.handler.CustomAccessDeniedHandler;
import gsm.gsmjava.global.security.handler.CustomAuthenticationEntryPointHandler;
import gsm.gsmjava.global.filter.JwtReqFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPointHandler authenticationEntryPoint;
    private final JwtReqFilter jwtReqFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final LoggingFilter loggingFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.exceptionHandling(handling -> handling
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint));

        http.sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.addFilterBefore(jwtReqFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter, JwtReqFilter.class);
        http.addFilterBefore(loggingFilter, ExceptionHandlerFilter.class);

        http.authorizeHttpRequests(httpRequests -> httpRequests

                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/refresh").permitAll()

                .requestMatchers(HttpMethod.POST, "/user/signup").hasAnyAuthority(Authority.TEMP.name())
                .requestMatchers(HttpMethod.GET, "/user/me").hasAnyAuthority(Authority.USER.name())
                .requestMatchers(HttpMethod.PUT, "/user/me").hasAnyAuthority(Authority.USER.name())
                .requestMatchers(HttpMethod.PATCH, "/user/me/resume").hasAnyAuthority(Authority.USER.name())

                .requestMatchers(HttpMethod.POST, "/posting").hasAnyAuthority(Authority.TEACHER.name())

                .requestMatchers(HttpMethod.POST, "/file/resume").hasAnyAuthority(Authority.TEMP.name(), Authority.USER.name())
                .requestMatchers(HttpMethod.POST, "/file/posting").hasAnyAuthority(Authority.TEACHER.name())

                .requestMatchers(HttpMethod.GET, "/health").permitAll()

                .anyRequest().denyAll()
        );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("*"));

        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()));

        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
