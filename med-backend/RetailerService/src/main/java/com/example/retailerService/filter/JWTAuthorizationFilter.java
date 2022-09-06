package com.example.retailerService.filter;

import com.example.retailerService.dto.response.ConnValidationResponse;
import com.example.retailerService.security.SecurityConstants;
import com.example.retailerService.utils.Utilities;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final RestTemplate restTemplate;
    private final String ADMIN_BASE_URL;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String bearerToken;
        try {
            bearerToken = request.getHeader(SecurityConstants.AUTH_HEADER);
        } catch (Exception e) {
            e.printStackTrace();
            filterChain.doFilter(request, response);
            return;
        }

        if (!(Utilities.validBearerToken(bearerToken))) {
            filterChain.doFilter(request, response);
            return;
        }

//        String authToken = bearerToken.replace(SecurityConstants.JWT_PREFIX, "");
//        Optional<TokensEntity> tokensEntity = tokensRedisService.findById(authToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstants.AUTH_HEADER,bearerToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ConnValidationResponse connValidationResponse  =  restTemplate.exchange(
                ADMIN_BASE_URL+"/auth/validate/getToken",
                HttpMethod.GET,
                entity,
                ConnValidationResponse.class)
                .getBody();


        if (connValidationResponse == null) {
            filterChain.doFilter(request, response);
            return;
        }
        try {

            if (!Jwts.parser().isSigned(connValidationResponse.getToken())) {
                filterChain.doFilter(request, response);
                return;
            }


            String token = connValidationResponse.getToken();
            Jws<Claims> authClaim = Jwts.parser().setSigningKey(SecurityConstants.PRIVATE_KEY).parseClaimsJws(token);
            String username = authClaim.getBody().getSubject();
            String userId = authClaim.getBody().getId();

            List<Map<String, String>> authorities = (List<Map<String, String>>) authClaim.getBody().get(SecurityConstants.AUTHORITIES_HEADER);
            List<GrantedAuthority> grantedAuthorities = authorities.stream().map(map -> new SimpleGrantedAuthority(map.get("authority")))
                    .collect(Collectors.toList());

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            request.setAttribute(SecurityConstants.USER_NAME, username);
            request.setAttribute(SecurityConstants.AUTHORITIES_HEADER, grantedAuthorities);
            request.setAttribute(SecurityConstants.JWT_TOKEN, token);
            request.setAttribute(SecurityConstants.USER_ID,userId );

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
        }


        filterChain.doFilter(request, response);

    }
}

