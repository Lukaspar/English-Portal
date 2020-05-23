package com.lukaspar.ep.common.security;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/authentication-module/users/login";
    public static final String AUTH_LOGIN_METHOD = "POST";

    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "english-portal";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final int TOKEN_EXPIRATION_TIME_MILLISECONDS = 1800000;

}
