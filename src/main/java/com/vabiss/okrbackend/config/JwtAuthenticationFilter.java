
package com.vabiss.okrbackend.config;

import com.vabiss.okrbackend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        final String jwt;
        final String username;

        Enumeration<String> headers = request.getHeaderNames();
        for (int i = 0; i < 20; i++) {
            if (headers.hasMoreElements()) {
                System.out.println("header names:" + headers.nextElement());
            }
        }
        System.out.println(header);
        System.out.println("hello");

        if (header == null || !header.startsWith("Bearer ")) {
            System.out.println("hello1");
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("hello2");

        jwt = header.substring(7);
        username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("hello3");

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.tokenControl(jwt, userDetails)) {
                System.out.println("hello4");

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        System.out.println("hello5");

        filterChain.doFilter(request, response);
    }

}
