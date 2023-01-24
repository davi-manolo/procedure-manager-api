package com.procedure.manager.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstantsUtils {

    public static final String SECRET = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 900000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String URL_LOGIN = "/v1/login";

}
