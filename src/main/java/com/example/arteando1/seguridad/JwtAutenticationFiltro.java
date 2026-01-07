package com.example.arteando1.seguridad;

import com.example.arteando1.modelos.Usuario;
import com.example.arteando1.repositorios.UsuarioRepositorio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAutenticationFiltro extends OncePerRequestFilter {
    private final JwtServicio jwtService;
    private final UsuarioRepositorio usuarioRepositorio;

    // Lista de rutas públicas que no requieren autenticación
    private static final List<String> RUTAS_PUBLICAS = List.of(
            "/api/usuarios/login",
            "/api/usuarios/registro/cliente",
            "/api/usuarios/registro/admin",
            "/api/tests/listar",
            "/api/categorias",
            "/swagger-ui",
            "/v3/api-docs",
            "/api-docs"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        // Ignorar rutas públicas y eventos específicos
        return RUTAS_PUBLICAS.stream().anyMatch(path::startsWith) || path.matches("/api/eventos/\\d+");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String email;

        try {
            email = jwtService.extractEmail(jwt);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido");
            return;
        }

        // Buscar usuario directamente por email
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Usuario no encontrado");
            return;
        }

        Usuario usuario = usuarioOpt.get();

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            // Validar token
            if (jwtService.isTokenValid(jwt, usuario)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        usuario.getEmail(), // autenticación por email
                        null,
                        List.of(new SimpleGrantedAuthority(usuario.getRol().name()))
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expirado o inválido");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
