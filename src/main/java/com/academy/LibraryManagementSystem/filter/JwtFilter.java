package com.academy.LibraryManagementSystem.filter;

import com.academy.LibraryManagementSystem.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_SCHEMA = "Bearer";
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String requestTokenHeader = request.getHeader(AUTHORIZATION);

        if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER_SCHEMA)) {
            String token = requestTokenHeader.substring(BEARER_SCHEMA.length());

            String username = jwtUtils.getUsername(token);

            if (jwtUtils.validateToken(token)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(userDetails);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        } else {
            logger.error("Authorization header is missing");
        }

        filterChain.doFilter(request, response);
    }

}
