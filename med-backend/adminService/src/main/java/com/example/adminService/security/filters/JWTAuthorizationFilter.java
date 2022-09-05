package com.example.adminService.security.filters;

import com.example.adminService.kafka.models.AppUser;
import com.example.adminService.services.TokensRedisService;
import com.example.adminService.kafka.models.redis.TokensEntity;
import com.example.adminService.security.SecurityConstants;
import com.example.adminService.services.UserService;
import com.example.adminService.utils.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final TokensRedisService tokensRedisService;
    private final UserService userService;

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

        String authToken = bearerToken.replace(SecurityConstants.JWT_PREFIX, "");

        Optional<TokensEntity> tokensEntity = tokensRedisService.findById(authToken);

        if (!tokensEntity.isPresent()) {
            filterChain.doFilter(request, response);
            return;
        }
        try {

            if (!Jwts.parser().isSigned(tokensEntity.get().getAuthenticationToken())) {
                filterChain.doFilter(request, response);
                return;
            }


            String token = tokensEntity.get().getAuthenticationToken();
            Jws<Claims> authClaim = Jwts.parser().setSigningKey(SecurityConstants.PRIVATE_KEY).parseClaimsJws(token);
            String userId = authClaim.getBody().getId();
            String username = authClaim.getBody().getSubject();
            List<Map<String, String>> authorities = (List<Map<String, String>>) authClaim.getBody().get(SecurityConstants.AUTHORITIES_HEADER);
            List<GrantedAuthority> grantedAuthorities = authorities.stream().map(map -> new SimpleGrantedAuthority(map.get("authority")))
                    .collect(Collectors.toList());

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            AppUser appUser = userService.getUserByUsernameObject(username);
            if(!appUser.isActive()){
                response.sendError(HttpStatus.PAYMENT_REQUIRED.value(),"You Are Currently Blocked please contact Admin.");
                return;
            }


            request.setAttribute(SecurityConstants.USER_ID, userId);
            request.setAttribute(SecurityConstants.USER_NAME, username);
            request.setAttribute(SecurityConstants.AUTHORITIES_HEADER, grantedAuthorities);
            request.setAttribute(SecurityConstants.JWT_TOKEN, token);
            request.setAttribute(SecurityConstants.JWT_TOKEN_KEY, bearerToken);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
        }


        filterChain.doFilter(request, response);

    }
    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}

