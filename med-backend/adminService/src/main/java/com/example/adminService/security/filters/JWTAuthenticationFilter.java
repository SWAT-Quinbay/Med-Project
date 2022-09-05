package com.example.adminService.security.filters;


import com.example.adminService.kafka.models.AppUser;
import com.example.adminService.services.TokensRedisService;
import com.example.adminService.dto.responses.ConnValidationResponse;
import com.example.adminService.dto.JwtAuthenticationModel;
import com.example.adminService.kafka.models.redis.TokensEntity;
import com.example.adminService.security.SecurityConstants;
import com.example.adminService.services.UserService;
import com.example.adminService.utils.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private ObjectMapper mapper=new ObjectMapper();

    private final TokensRedisService tokensRedisService;
    private final UserService userService;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {

            JwtAuthenticationModel authModel = mapper.readValue(request.getInputStream(), JwtAuthenticationModel.class);
//            JwtAuthenticationModel authModel  = JwtAuthenticationModel
//                    .builder()
//                    .username(request.getParameter("username"))
//                    .password(request.getParameter("password")).build();
            log.info(authModel.toString());

            Authentication authentication = new UsernamePasswordAuthenticationToken(authModel.getUsername(), authModel.getPassword());
            return authenticationManager.authenticate(authentication);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authResult)
            throws IOException,UsernameNotFoundException {

        AppUser appUser = userService.getUserByUsernameObject(authResult.getName());

        if(!appUser.isActive()){
            response.sendError(HttpStatus.PAYMENT_REQUIRED.value(),"You Are Currently Blocked please contact Admin.");
            return;
        }


        String token = Jwts.builder()
                .setId(String.valueOf(appUser.getId()))
                .setSubject(authResult.getName())
                .claim(SecurityConstants.AUTHORITIES_HEADER, authResult.getAuthorities())
                .setIssuer(request.getPathInfo())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60*60*1000))// 1 hr
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.PRIVATE_KEY)
                .compact();

        log.info(token);

        TokensEntity tokensEntity = TokensEntity.builder()
                .id(Utilities.generateUuid())
                .userId(String.valueOf(appUser.getId()))
                .authenticationToken(token)
                .username(authResult.getName())
                .createdBy("SYSTEM").createdOn(LocalDateTime.now())
                .modifiedBy("SYSTEM").modifiedOn(LocalDateTime.now())
                .build();

        tokensEntity = tokensRedisService.save(tokensEntity);
        response.addHeader(SecurityConstants.AUTH_HEADER, SecurityConstants.JWT_PREFIX + tokensEntity.getId());

        ConnValidationResponse respModel = ConnValidationResponse.builder()
                .userId(String.valueOf(appUser.getId()))
                .username(authResult.getName())
                .roles((List<GrantedAuthority>) authResult.getAuthorities())
                .status(HttpStatus.OK.name())
                .token(SecurityConstants.JWT_PREFIX+tokensEntity.getId())
                .methodType(request.getMethod())
                .isAuthenticated(true).build();

        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(mapper.writeValueAsBytes(respModel));
    }
}
