package pl.maropce.etutor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.maropce.etutor.user.AppUserDetails;
import pl.maropce.etutor.user.UserDTO;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {


    private final ObjectMapper objectMapper;

    public WebSecurityConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/lessons/student/{id}").authenticated()
                        .requestMatchers("/api/students").hasAuthority("TEACHER")
                        .anyRequest().permitAll()
                );

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));


        http.formLogin(formLogin -> {
           formLogin.successHandler((request, response, authentication) -> {
               AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

               UserDTO userDTO = new UserDTO();
               userDTO.setFirstName(userDetails.getUsername());
               userDTO.setRoles(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
               userDTO.setId(userDetails.getId());

                response.setStatus(200);
                response.setContentType("application/json");
                response.getWriter().write(objectMapper.writeValueAsString(userDTO));
            });

           formLogin.failureHandler((request, response, authentication) -> {
               System.out.println("ZLE DANE!!!!");
               response.setStatus(401);
           });

        });

        http.logout(logout -> logout.logoutSuccessHandler(
                (request, response, authentication) -> response.setStatus(200)
        ));

        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
