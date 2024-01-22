package com.cus.healthcare.system.jwt;

import com.cus.healthcare.system.dto.response.CustomResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtUsernamePasswordAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            UsernamePasswordAuthenticationRequest usernamePasswordAuthenticationRequest =
                    new ObjectMapper().readValue(
                            request.getInputStream(),
                            UsernamePasswordAuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    usernamePasswordAuthenticationRequest.getUsername(),
                    usernamePasswordAuthenticationRequest.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_" + usernamePasswordAuthenticationRequest.getRoleId())));

            Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();

        String userRole = authResult.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .orElse("");
        //"role", userRole
        Object userData = Map.of("token", jwtConfig.getTokenPrefix() + token, "role", userRole);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setData(userData);
        customResponse.setSuccessful(true); // Assuming successful authentication
        customResponse.setStatusCode(HttpServletResponse.SC_OK); // HTTP status code for success
        customResponse.setMessage("Login successful");

        // Convert custom response to JSON and write to response body
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(customResponse));
    }


}
