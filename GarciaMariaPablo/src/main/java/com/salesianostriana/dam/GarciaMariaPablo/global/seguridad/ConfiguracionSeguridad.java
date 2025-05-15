package com.salesianostriana.dam.GarciaMariaPablo.global.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {

    @Autowired
    private CodificadorContrasenas codificadorContrasenas;
    @Autowired
    private ServicioCustomUserDetails servicioCustomUserDetails;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/", "/css/**", "/js/**", "/img/**","/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/contacto","/nosotros","/politica-privacidad","/faq").permitAll()
                        .requestMatchers(HttpMethod.GET,"/dashboard","/perfil").authenticated()
//                      .anyRequest().permitAll() // Todo lo demás no requiere autenticación
                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/dashboard")
//                        .permitAll()
//                )
                .formLogin(form -> form.disable()) // Desactiva el login automático
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .headers(headers -> headers
                        .frameOptions(f -> f
                                .disable()))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return auth.build();
    }



}
