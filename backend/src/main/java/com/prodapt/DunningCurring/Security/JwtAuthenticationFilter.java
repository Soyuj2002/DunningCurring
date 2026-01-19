package com.prodapt.DunningCurring.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException; // Import this

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // 1. Check if token exists
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            
            // 2. SAFETY FIX: Try to extract username, but catch Expired Tokens
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (ExpiredJwtException e) {
                // Token is expired. Ignore it and allow the request to proceed as Anonymous.
                System.out.println("JWT Token has expired. Proceeding without auth.");
            } catch (Exception e) {
                System.out.println("Error parsing JWT: " + e.getMessage());
            }
        }

        // 3. Validate Token and set Context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            
            // Re-check validity here (handled by JwtUtil)
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
             // --- ADD THESE DEBUG LOGS ---
//                System.out.println("🔐 SECURITY FILTER DEBUG:");
//                System.out.println("   -> User: " + userDetails.getUsername());
//                System.out.println("   -> Raw Token Role: " + jwtUtil.extractClaim(jwt, claims -> claims.get("role", String.class)));
//                System.out.println("   -> Assigned Authorities: " + userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // 4. Continue the filter chain
        filterChain.doFilter(request, response);
    }
}