package com.example.arteando1.seguridad;

import com.example.arteando1.servicios.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAutenticationFiltro jwtAutenticationFiltro;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // 🔓 Endpoints públicos (sin token)
                        .requestMatchers(
                                "/api/usuarios/login",
                                "/api/usuarios/registro/cliente",
                                "/api/tests/listar",
                                "/api/categorias/listar",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Rutas protegidas para clientes autenticados
                        .requestMatchers(HttpMethod.GET, "/api/clientes/perfil").authenticated()
                        //                                "/api/usuarios/perfil",
                        .requestMatchers(HttpMethod.PUT, "/api/clientes/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/clientes/usuario/**").authenticated()

                        //RUTAS ADMIN PARA TEST
                        .requestMatchers(HttpMethod.POST, "/api/tests/crear").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT, "/api/tests/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/tests/**").hasAuthority("admin")

                        //  RUTAS ADMIN PARA PREGUNTAS
                        .requestMatchers(HttpMethod.POST, "/api/preguntas/crear").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT, "/api/preguntas/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/preguntas/**").hasAuthority("admin")

                        //RUTAS ADMIN PARA OPCIONES
                        .requestMatchers(HttpMethod.POST, "/api/opciones/crear").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT, "/api/opciones/**").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/opciones/**").hasAuthority("admin")


                        // Cualquier otro endpoint requiere autenticación JWT
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAutenticationFiltro, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



}
