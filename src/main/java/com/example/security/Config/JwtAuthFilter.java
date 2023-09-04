package com.example.security.Config;

import com.example.security.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final UserDao userDao;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Extract JWT token from the request
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        // Check if the header is missing or doesn't start with "Bearer"
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            // Continue processing the request without authentication
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token from the header
        String jwtToken = authHeader.substring(7);

        // Extract user email from JWT token
        String userEmail = jwtUtils.extractUsername(jwtToken);

        // Check if there is no existing authentication
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Fetch user details from the database
            UserDetails userDetails = userDao.findUserByEmail(userEmail);

            // Check if the JWT token is valid for the user
            if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
                // Create an authentication token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication token in the Spring Security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue processing the request
        filterChain.doFilter(request, response);
    }
}