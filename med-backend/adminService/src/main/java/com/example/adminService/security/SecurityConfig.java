package com.example.adminService.security;


import com.example.adminService.services.UserService;
import com.example.adminService.services.implement.ApplicationUserDetailServiceImp;
import com.example.adminService.services.TokensRedisService;
import com.example.adminService.security.filters.JWTAuthenticationFilter;
import com.example.adminService.security.filters.JWTAuthorizationFilter;
import com.example.adminService.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ApplicationUserDetailServiceImp applicationUserDetailServiceImp;

    @Autowired
    private TokensRedisService redisService;

    @Autowired
    private UserService userService;
    private static final String[] AUTH_WHITELIST = {
            "/resources/**",
            "/rest/**",
            // -- Swagger UI v2
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // other public endpoints of your API may be appended to this array
             "/users/add",
            "/login*",
//            "/inventory/detail/**"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();

        http.authorizeRequests().antMatchers("/auth/**").authenticated();
        http.authorizeRequests()
                .antMatchers("/inventory/detail/**","/stock*/**")
                .hasAnyAuthority(Constants.DEALER_ROLE,Constants.RETAILER_ROLE,Constants.ADMIN_ROLE);
        http.authorizeRequests().antMatchers("*").hasAuthority(Constants.ADMIN_ROLE);


        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), redisService,userService));
        http.addFilterBefore(new JWTAuthorizationFilter(redisService), JWTAuthenticationFilter.class);
        http.httpBasic();

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        authenticationProvider.setUserDetailsService(applicationUserDetailServiceImp);

        return authenticationProvider;
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH",
                "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type",
                "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
