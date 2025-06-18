package com.example.servicebooking.config;

import com.example.servicebooking.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        logger.debug("Request Path: {}", path);

        // Skip JWT validation for public endpoints
        if (path.startsWith("/api/auth/")) {
            logger.debug("Skipping JWT check for public endpoint");
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String email = null;

        logger.debug("Authorization header: {}", authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Remove "Bearer " prefix
            logger.debug("JWT token extracted: {}", jwt);

            try {
                email = jwtUtil.extractEmail(jwt);
                logger.debug("Email extracted from token: {}", email);
            } catch (Exception e) {
                logger.error("JWT extraction failed: {}", e.getMessage());
            }
        } else {
            logger.warn("Authorization header missing or does not start with Bearer");
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;
            try {
                userDetails = userDetailsService.loadUserByUsername(email);
                logger.debug("UserDetails loaded: {}", userDetails.getUsername());
            } catch (Exception e) {
                logger.error("UserDetailsService failed to load user: {}", e.getMessage());
            }

            if (userDetails != null && jwtUtil.validateToken(jwt, userDetails)) {
                logger.debug("JWT token is valid, setting authentication in context");
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                logger.warn("JWT token is invalid or userDetails is null");
            }
        } else if (email == null) {
            logger.warn("Email extracted from token is null or authentication already exists");
        }

        filterChain.doFilter(request, response);
    }
}
