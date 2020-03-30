package com.lukaspar.ep.security;

import io.jsonwebtoken.*;
import liquibase.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.lukaspar.ep.security.SecurityConstants.*;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String token = request.getHeader(TOKEN_HEADER);

        if (StringUtils.isNotEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            try {
                final var signingKey = secretKey.getBytes();

                final var parsedToken = Jwts.parserBuilder()
                        .setSigningKey(signingKey)
                        .build()
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));

                final var username = parsedToken
                        .getBody()
                        .getSubject();

                final var authorities = ((List<?>) parsedToken.getBody()
                        .get("roles")).stream()
                        .map(authority -> new SimpleGrantedAuthority((String) authority))
                        .collect(Collectors.toList());

                final var authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (JwtException exception) {
                log.warn("Jwt token validation failed: {}", exception.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
