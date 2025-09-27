package dev.damianlis.identity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
class IdentitySecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1) uprawnienia
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/identity/**").permitAll()
                        .anyRequest().authenticated()
                )
                // 2) CSRF: wyłącz
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/identity/**")
                )
                // 3) H2 używa ramek — zezwól z tej samej domeny
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )
                // 4) jakaś forma logowania na resztę endpointów
                .httpBasic(Customizer.withDefaults()); // lub .formLogin()

        return http.build();
    }

}
