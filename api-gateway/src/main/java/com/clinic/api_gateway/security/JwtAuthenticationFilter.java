package com.clinic.api_gateway.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
private final JwtService jwtService;

public JwtAuthenticationFilter(
        JwtService jwtService) {

    this.jwtService = jwtService;
}

@Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

    String authHeader =
            request.getHeader("Authorization");

    if (authHeader == null ||
            !authHeader.startsWith("Bearer ")) {

        filterChain.doFilter(request, response);
        return;
    }

    String token =
            authHeader.substring(7);

    if (!jwtService.isTokenValid(token)) {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType("application/json");

        response.getWriter().write("""
        {
            "status":401,
            "error":"Unauthorized",
            "message":"Token inválido"
        }
        """);

        return;
    }

    Claims claims =
            jwtService.extractAllClaims(token);

    String email =
            claims.getSubject();

    String rol =
            claims.get("rol", String.class);

    UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    List.of(
                            new SimpleGrantedAuthority(
                                    "ROLE_" + rol)));

    SecurityContextHolder.getContext()
            .setAuthentication(authentication);

    filterChain.doFilter(
            request,
            response);
}

}
