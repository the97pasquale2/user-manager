package demo.usermanager.config;

import demo.usermanager.auth.IdentityProvider;
import demo.usermanager.auth.LogLoggedUserFilter;
import demo.usermanager.auth.LoggedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    @Autowired
    IdentityProvider identityProvider;

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainLogin(HttpSecurity http) throws Exception {
        http.
                securityMatcher("/auth/login")
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new LogLoggedUserFilter(identityProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/auth/login").permitAll()
                );
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChainPrivate(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/private/**")
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new LogLoggedUserFilter(identityProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/private/**").authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(
                                jwtConfigurer -> {
                                    jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter());
                                }
                        )
                );
        return http.build();
    }

    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        Converter<Jwt, Collection<GrantedAuthority>> authoritiesConverter = jwt -> {
            JwtGrantedAuthoritiesConverter authConverter = new JwtGrantedAuthoritiesConverter();

            Collection<GrantedAuthority> authorities = new ArrayList<>(authConverter.convert(jwt));
            LoggedUser loggedUser = LoggedUser.fromTokenClaims(jwt.getClaims());
            loggedUser.getPermissions().forEach(r ->
                    authorities.add(new SimpleGrantedAuthority(r))
            );

            return authorities;
        };

        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }

    @Bean
    static GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

}
