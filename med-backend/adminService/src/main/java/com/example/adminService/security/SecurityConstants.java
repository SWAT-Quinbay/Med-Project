package com.example.adminService.security;

public class SecurityConstants {
    public static final String AUTH_HEADER = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";
    public static final String PRIVATE_KEY = "med@12*&34";
    public static final String AUTHORITIES_HEADER = "roles";

    public static final String USER_ID = "userId";
    public static final String USER_NAME = "username";
    public static final String JWT_TOKEN = "JWT";
    public static final String JWT_TOKEN_KEY = "JWT_key";

    public static final long JWT_EXPIRATION_TIME = 60*60*1000;//in milliseconds(1hr)
    public static final long REDIS_TIME_TO_LIVE = 24*60*60;//in seconds (1day)
}
